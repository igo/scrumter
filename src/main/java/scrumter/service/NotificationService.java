package scrumter.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		emailService.sendEmail(null, "groupMembershipAdd", "skupina");
		notification.setCreated(new Date());
		notificationRepository.create(notification);
	}

	public void addCommentNotification(Status status, User commentAuthor) {
		// create list of notified user to prevent multiple notifications
		Set<User> notifiedUsers = new HashSet<User>();

		// add notification to all who also commented
		List<Comment> comments = status.getComments();
		for (Comment comment : comments) {
			User user = comment.getAuthor();
			if (!notifiedUsers.contains(user)) {
				Notification notification = new Notification("comment", comment.getAuthor());
				notification.addMeta("user", commentAuthor.getId().toString());
				notification.addMeta("status", status.getId().toString());
				addNotification(notification);
				notifiedUsers.add(comment.getAuthor());
			}
		}

		// add notification to status author
		if (!notifiedUsers.contains(status.getAuthor())) {
			Notification notification = new Notification("comment", status.getAuthor());
			notification.addMeta("user", commentAuthor.getId().toString());
			notification.addMeta("status", status.getId().toString());
			addNotification(notification);
			notifiedUsers.add(status.getAuthor());
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

	public void dismissNotifications(User user) {
		List<Notification> notifications = getNotifications(user);
		for (Notification notification : notifications) {
			dismissNotification(notification);
		}
	}

}
