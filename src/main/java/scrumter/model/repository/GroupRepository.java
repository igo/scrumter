package scrumter.model.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.entity.Group;
import scrumter.model.entity.User;
import scrumter.model.entity.Group.GroupType;

@Repository
public class GroupRepository {

	private Logger logger = Logger.getLogger(GroupRepository.class);

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void create(Group group) {
		logger.info("Creating group: " + group);
		em.persist(group);
	}

	@Transactional
	public void update(Group group) {
		logger.info("Updating group: " + group);
		em.merge(group);
	}

	public Group findById(Long id) {
		return em.find(Group.class, id);
	}

	public Group findByLink(String link) {
		Query query = em.createNamedQuery("Group.findByLink");
		query.setParameter("link", link);
		return (Group) query.getSingleResult();
	}

	public List<Group> findAll() {
		Query query = em.createNamedQuery("Group.findAll");
		return query.getResultList();
	}

	public List<Group> findAllByMemberAndType(User user, GroupType type) {
		Query query = em.createNamedQuery("Group.findAllByMemberAndType");
		query.setParameter("user", user);
		query.setParameter("type", type);
		return query.getResultList();
	}

	public Long countMembers(Group group) {
		Query query = em.createNamedQuery("Group.countMembers");
		query.setParameter("group", group);
		return (Long) query.getSingleResult();
	}

}
