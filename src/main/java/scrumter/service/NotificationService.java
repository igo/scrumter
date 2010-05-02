package scrumter.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.Comment;
import scrumter.model.MetaData;
import scrumter.model.Notification;
import scrumter.model.Status;
import scrumter.model.User;

@Service
public class NotificationService {

	private Logger logger = Logger.getLogger(NotificationService.class);

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	private SecurityService securityService; 

	public void addCommentNotification(Status status, User author) {
		// get current user who will not receive notification
		User currentUser = securityService.getCurrentUser();

		// create list of notified user to prevent multiple notifications
		Set<User> notifiedUsers = new HashSet<User>();
		
		// add notification to all who also commented
		List<Comment> comments = status.getComments();
		for (Comment comment : comments) {
			User user = comment.getAuthor();
			if (!user.equals(currentUser) && !notifiedUsers.contains(user)) {
				Notification notification = new Notification("comment", comment.getAuthor());
				notification.addMeta("user", author.getId().toString());
				notification.addMeta("status", status.getId().toString());
				addNotification(notification);
				notifiedUsers.add(comment.getAuthor());
			}
		}

		// add notification to status author
		if (!status.getAuthor().equals(currentUser) && !notifiedUsers.contains(status.getAuthor())) {
			Notification notification = new Notification("comment", status.getAuthor());
			notification.addMeta("user", author.getId().toString());
			notification.addMeta("status", status.getId().toString());
			addNotification(notification);
			notifiedUsers.add(status.getAuthor());
		}
	}

	@Transactional
	public void addNotification(Notification notification) {
		logger.debug("Adding notification: " + notification);
		notification.setCreated(new Date());
		for (MetaData meta : notification.getMeta()) {
			em.persist(meta);
		}
		em.persist(notification);
		em.flush();
	}

	public List<Notification> findNotificationsForUser(User owner) {
		Query query = em.createNamedQuery("Notification.findAllByOwner");
		query.setParameter("owner", owner);
		return query.getResultList();
	}

}
