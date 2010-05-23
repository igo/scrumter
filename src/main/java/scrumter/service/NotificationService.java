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

import scrumter.model.entity.Comment;
import scrumter.model.entity.MetaData;
import scrumter.model.entity.Notification;
import scrumter.model.entity.Status;
import scrumter.model.entity.User;

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

	public Notification findNotificationById(Long id) {
		return em.find(Notification.class, id);
	}

	public List<Notification> findNotificationsForUser(User owner) {
		Query query = em.createNamedQuery("Notification.findAllByOwner");
		query.setParameter("owner", owner);
		return query.getResultList();
	}

	@Transactional
	public int dismiss(Notification notification) {
		Set<MetaData> metas = notification.getMeta();
		for (MetaData meta : metas) {
			em.remove(meta);
		}
		notification.getMeta().clear();
		em.merge(notification);
		em.flush();
		Query query = em.createNamedQuery("Notification.delete");
		query.setParameter("notification", notification);
		return query.executeUpdate();
	}

	@Transactional
	public int dismissAllForUser(User user) {
		List<Notification> notifications = findNotificationsForUser(user);
		int count = 0;
		for (Notification notification : notifications) {
			count += dismiss(notification);
		}
		return count;
	}

}
