package scrumter.model.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.entity.MetaData;
import scrumter.model.entity.Notification;
import scrumter.model.entity.User;

@Repository
public class NotificationRepository {

	private Logger logger = Logger.getLogger(NotificationRepository.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private MetaDataRepository metaDataRepository;

	@Transactional
	public void create(Notification notification) {
		logger.info("Creating notification: " + notification);
		for (MetaData meta : notification.getMeta()) {
			metaDataRepository.create(meta);
		}
		em.persist(notification);
	}

	@Transactional
	public void delete(Notification notification) {
		logger.debug("Deleting notification: " + notification);
		Set<MetaData> metas = notification.getMeta();
		for (MetaData meta : metas) {
			metaDataRepository.delete(meta);
		}
		notification.getMeta().clear();
		em.remove(notification);
	}

	public Notification findById(Long id) {
		return em.find(Notification.class, id);
	}

	public List<Notification> findAllByOwner(User owner) {
		Query query = em.createNamedQuery("Notification.findAllByOwner");
		query.setParameter("owner", owner);
		return query.getResultList();
	}

}
