package scrumter.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.Group;

@Service
public class GroupService {

	private Logger logger = Logger.getLogger(GroupService.class);

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@Transactional
	public void addGroup(Group group) {
		logger.info("Adding group: " + group);
		em.persist(group);
	}

	@Transactional
	public void saveGroup(Group group) {
		logger.info("Saving group: " + group);
		em.merge(group);
	}

	@Transactional
	public void deleteAllGroups() {
		Query query = em.createNamedQuery("Group.deleteAll");
		query.executeUpdate();
	}

	public List<Group> findAllGroups() {
		Query query = em.createNamedQuery("Group.findAll");
		return query.getResultList();
	}
}
