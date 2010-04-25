package scrumter.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.Group;
import scrumter.model.User;
import scrumter.model.Group.GroupType;

@Service
public class GroupService {

	private Logger logger = Logger.getLogger(GroupService.class);

	@PersistenceContext
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

	public Group findGroupById(Long id) {
		Query query = em.createNamedQuery("Group.findById");
		query.setParameter("id", id);
		return (Group) query.getSingleResult();
	}

	public Group findGroupByLink(String link) {
		Query query = em.createNamedQuery("Group.findByLink");
		query.setParameter("link", link);
		return (Group) query.getSingleResult();
	}

	public List<Group> findAllGroups() {
		Query query = em.createNamedQuery("Group.findAll");
		return query.getResultList();
	}

	public List<Group> findGroupsByMember(User user) {
		Query query = em.createNamedQuery("Group.findAllByMember");
		query.setParameter("user", user);
		return query.getResultList();
	}

	public List<Group> findGroupsByMemberAndType(User user, GroupType type) {
		Query query = em.createNamedQuery("Group.findAllByMemberAndType");
		query.setParameter("user", user);
		query.setParameter("type", type);
		return query.getResultList();
	}

	public Long countGroupsByTypeAndUser(GroupType type, User user) {
		Query query = em.createNamedQuery("Group.countByTypeAndUser");
		query.setParameter("type", type);
		query.setParameter("user", user);
		return (Long) query.getSingleResult();
	}

	public Long countMembers(Group group) {
		Query query = em.createNamedQuery("Group.countMembers");
		query.setParameter("group", group);
		return (Long) query.getSingleResult();
	}

}
