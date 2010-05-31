package scrumter.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.entity.Comment;
import scrumter.model.entity.Notification;
import scrumter.model.entity.Status;
import scrumter.model.entity.User;
import scrumter.model.repository.NotificationRepository;

@Service
public class NotificationService {

	private Logger logger = Logger.getLogger(NotificationService.class);

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private EmailService emailService;

	public void addNotification(Notification notification) {
		notification.setCreated(new Date());
		notificationRepository.create(notification);
	}

	public void addCommentNotification(Status status, Comment newComment) {
		logger.debug("Adding comment " + newComment + " notification on status " + status);
		User commentAuthor = newComment.getAuthor();
		User statusAuthor = status.getAuthor();
		// create list of notified user to prevent multiple notifications
		Set<User> notifiedUsers = new HashSet<User>();

		// add notification to status author
		if (!statusAuthor.equals(commentAuthor)) {
			Notification notification = new Notification("comment", statusAuthor);
			notification.addMeta("user", commentAuthor.getId().toString());
			notification.addMeta("status", status.getId().toString());
			addNotification(notification);
			notifiedUsers.add(statusAuthor);
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
						.getUsername(), statusAuthor.getCompany(), newComment.getComment(), commentAuthor.getFullName());
			}
		}

		// add notification to all who also commented
		Set<User> emailUsers = new HashSet<User>();
		List<Comment> comments = status.getComments();
		for (Comment comment : comments) {
			User user = comment.getAuthor();
			if (!notifiedUsers.contains(user) && !user.equals(commentAuthor) && !user.equals(statusAuthor)) {
				Notification notification = new Notification("comment", comment.getAuthor());
				notification.addMeta("user", commentAuthor.getId().toString());
				notification.addMeta("status", status.getId().toString());
				addNotification(notification);
				notifiedUsers.add(comment.getAuthor());
				if (user.getEmailCommentOnTouchedStatus()) {
					emailUsers.add(user);
				}
			}
		}

		if (emailUsers.size() > 0) {
			// 1 - status, 2 - status id, 3 - status author username, 4 - status author company, 5 - comment, 6 - status author, 7 - comment author
			emailService.sendEmailsFromTemplate(emailUsers, "notification.statusComment", status.getStatus(), status.getId().toString(), statusAuthor
					.getUsername(), statusAuthor.getCompany(), newComment.getComment(), statusAuthor.getFullName(), commentAuthor.getFullName());
		}
	}

	public Notification getNotificationById(Long id) {
		return notificationRepository.findById(id);
	}

	public List<Notification> getNotifications(User owner) {
		return notificationRepository.findAllByOwner(owner);
	}

	public void dismissNotification(Notification notification) {
		notificationRepository.delete(notification);
	}

	@Transactional
	public void dismissNotifications(User user) {
		List<Notification> notifications = getNotifications(user);
		for (Notification notification : notifications) {
			dismissNotification(notification);
		}
	}

}
