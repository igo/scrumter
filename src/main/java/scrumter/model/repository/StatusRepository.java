package scrumter.model.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.entity.Group;
import scrumter.model.entity.Status;
import scrumter.model.entity.User;

@Repository
public class StatusRepository {

	private Logger logger = Logger.getLogger(UserRepository.class);

	@PersistenceContext
	EntityManager em;

	private void setQueryLimits(Query query, Integer startPosition, Integer maxResult) {
		if (startPosition != null) {
			query.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			query.setMaxResults(maxResult);
		}
	}

	@Transactional
	public void create(Status status) {
		logger.debug("Creating status: " + status);
		status.setCreated(new Date());
		em.persist(status);
	}

	@Transactional
	public void update(Status status) {
		logger.debug("Updating status: " + status);
		em.merge(status);
	}

	public Status findById(Long statusId) {
		return em.find(Status.class, statusId);
	}

	public List<Status> findAllByGroup(Group group, Integer startPosition, Integer maxResult) {
		Query query = em.createNamedQuery("Status.findAllByGroup");
		query.setParameter("group", group);
		setQueryLimits(query, startPosition, maxResult);
		return query.getResultList();
	}

	public List<Status> findAllAllowed(User user, Integer startPosition, Integer maxResult) {
		Query query = em.createNamedQuery("Status.findAllAllowed");
		query.setParameter("user", user);
		setQueryLimits(query, startPosition, maxResult);
		return query.getResultList();
	}

	public List<Status> findAllAllowedByAuthor(User user, User author, Integer startPosition, Integer maxResult) {
		Query query = em.createNamedQuery("Status.findAllAllowedByAuthor");
		query.setParameter("user", user);
		query.setParameter("author", author);
		setQueryLimits(query, startPosition, maxResult);
		return query.getResultList();
	}

	public Long countInGroup(Group group) {
		Query query = em.createNamedQuery("Status.countStatusesInGroup");
		query.setParameter("group", group);
		return (Long) query.getSingleResult();
	}

}
