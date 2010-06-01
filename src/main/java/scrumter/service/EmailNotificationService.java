package scrumter.service;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import scrumter.model.entity.Comment;
import scrumter.model.entity.Status;
import scrumter.model.entity.User;

@Service
public class EmailNotificationService {

	private Logger logger = Logger.getLogger(EmailNotificationService.class);

	@Autowired
	private EmailService emailService;

	@Async
	public void notifyCommentOnOwnStatus(Status status, Comment newComment) {
		logger.debug("Preparing e-mail notifications on CommentOnOwnStatus");
		User statusAuthor = status.getAuthor();
		User commentAuthor = newComment.getAuthor();

		// add notification to status author
		if (!statusAuthor.equals(commentAuthor)) {
			boolean sendEmailToStatusAuthor = statusAuthor.getEmailCommentOnOwnStatus();
			// check if status author also commented status and want be e-mailed
			if (!sendEmailToStatusAuthor && statusAuthor.getEmailCommentOnTouchedStatus()) {
				List<Comment> comments = status.getComments();
				for (Comment comment : comments) {
					if (comment.getAuthor().equals(statusAuthor)) {
						sendEmailToStatusAuthor = true;
						break;
					}
				}
			}
			// send e-mail to status author if he asked for it
			if (sendEmailToStatusAuthor) {
				// 1 - status, 2 - status id, 3 - status author username, 4 - status author company, 5 - comment, 6 - comment author
				emailService.sendEmailFromTemplate(statusAuthor, "notification.ownStatusComment", status.getStatus(), status.getId().toString(), statusAuthor
						.getUsername(), statusAuthor.getCompany(), newComment.getComment(), newComment.getAuthor().getFullName());
			}
		}

	}

	@Async
	public void notifyCommentOnTouchedStatus(Collection<User> users, Status status, Comment comment) {
		logger.debug("Preparing e-mail notifications on CommentOnTouchedStatus");
		for (User user : users) {
			if (!user.getEmailCommentOnTouchedStatus()) {
				users.remove(user);
			}
		}
		User statusAuthor = status.getAuthor();
		// 1 - status, 2 - status id, 3 - status author username, 4 - status author company, 5 - comment, 6 - status author, 7 - comment author
		emailService.sendEmailsFromTemplate(users, "notification.statusComment", status.getStatus(), status.getId().toString(), statusAuthor.getUsername(),
				statusAuthor.getCompany(), comment.getComment(), statusAuthor.getFullName(), comment.getAuthor().getFullName());
	}

	@Async
	public void notifyNewStatus(Collection<User> users, Status status) {
		logger.debug("Preparing e-mail notifications on NewStatus");
		User statusAuthor = status.getAuthor();
		// 1 - status, 2 - status id, 3 - status author username, 4 - status author company, 5 - status author
		emailService.sendEmailsFromTemplate(users, "notification.newStatus", status.getStatus(), status.getId().toString(), statusAuthor.getUsername(),
				statusAuthor.getCompany(), statusAuthor.getFullName());
	}

}
