package scrumter.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scrumter.controller.StatusController;
import scrumter.model.Status;
import scrumter.model.User;


@Service
public class StatusService {
	
	private Logger logger = Logger.getLogger(StatusService.class);


	@PersistenceContext
	EntityManager em;

	@Transactional
	public void addStatus(Status status) {
		logger.debug("Saving status: " + status);
		status.setCreated(new Date());
		em.persist(status);
		em.flush();
	}
	
	public List<Status> findStatusesByAuthor(User author, Integer startPosition, Integer maxResult) {
		Query query = em.createNamedQuery("Status.findAllByAuthor");
		query.setParameter("author", author);
		if (startPosition != null) {
			query.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			query.setMaxResults(maxResult);
		}
		return query.getResultList();
	}
	
	public List<Status> findFollowedStatuses(User follower, Integer startPosition, Integer maxResult) {
		Query query = em.createNamedQuery("Status.findAll");
//		query.setParameter("author", author);
		if (startPosition != null) {
			query.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			query.setMaxResults(maxResult);
		}
		return query.getResultList();
	}
	
	@Transactional
	public void deleteAllStatuses() {
		Query query = em.createNamedQuery("Status.deleteAll");
		query.executeUpdate();
	}
	
}