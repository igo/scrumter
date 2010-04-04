package scrumter.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.Comment;
import scrumter.model.Status;
import scrumter.model.User;


@Service
public class StatusService {
	
	private Logger logger = Logger.getLogger(StatusService.class);


	@PersistenceContext
	EntityManager em;

	@Transactional
	public void addStatus(Status status) {
		logger.debug("Adding status: " + status);
		status.setCreated(new Date());
		em.persist(status);
		em.flush();
	}
	
	@Transactional
	public void saveStatus(Status status) {
		logger.debug("Saving status: " + status);
		em.merge(status);
		em.flush();
	}
	
	public Status findStatusById(Long statusId) {
		return em.find(Status.class, statusId);
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
	
	public List<Status> findStatusesForUser(User user, Integer startPosition, Integer maxResult) {
		Query query = em.createNamedQuery("Status.findAllForUser");
		query.setParameter("user", user);
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
	
	@Transactional
	public void addComment(Comment comment) {
		logger.debug("Saving comment: " + comment);
		comment.setCreated(new Date());
		em.persist(comment);
		em.flush();
	}
	
}
