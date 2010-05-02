package scrumter.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.MetaData;
import scrumter.model.Notification;
import scrumter.model.User;

@Service
public class NotificationService {

	private Logger logger = Logger.getLogger(NotificationService.class);

	@PersistenceContext
	EntityManager em;

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
