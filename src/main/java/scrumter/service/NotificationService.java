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
	private EmailNotificationService emailNotificationService;

	@Autowired
	private UserService userService;

	public void addNotification(Notification notification) {
		notification.setCreated(new Date());
		notificationRepository.create(notification);
	}

	public void notifyNewStatus(Status status) {
		List<User> users = userService.getUsersForNewStatusNotification(status.getAllowedGroups());
		emailNotificationService.notifyNewStatus(users, status);
	}

	public void notifyNewComment(Status status, Comment newComment) {
		logger.debug("Notification on new comment in status " + status);
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
			// send e-mail to status author if he asked for it
			emailNotificationService.notifyCommentOnOwnStatus(status, newComment);
		}

		// add notification to all who also commented
		Set<User> emailUsers = new HashSet<User>();
		List<Comment> comments = status.getComments();
		for (Comment comment : comments) {
			User user = comment.getAuthor();
			if (!notifiedUsers.contains(user) && !user.equals(commentAuthor) && !user.equals(statusAuthor)) {
				Notification notification = new Notification("comment", user);
				notification.addMeta("user", commentAuthor.getId().toString());
				notification.addMeta("status", status.getId().toString());
				addNotification(notification);
				notifiedUsers.add(user);
				emailUsers.add(user);
			}
		}
		if (emailUsers.size() > 0) {
			emailNotificationService.notifyCommentOnTouchedStatus(emailUsers, status, newComment);
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
