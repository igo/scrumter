package scrumter.model.repository;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.entity.Group;
import scrumter.model.entity.User;

@Repository
public class UserRepository {

	private Logger logger = Logger.getLogger(UserRepository.class);

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void create(User user) {
		logger.info("Creating user: " + user);
		em.persist(user);
	}

	@Transactional
	public void update(User user) {
		logger.info("Updating user: " + user);
		em.merge(user);
	}

	public void refresh(User user) {
		logger.info("Refreshing user: " + user);
		em.refresh(user);
	}

	public User findById(Long id) {
		return em.find(User.class, id);
	}

	public User findByEmail(String email) {
		logger.debug("Finding user by email: " + email);
		Query query = em.createNamedQuery("User.findByEmail");
		query.setParameter("email", email);
		try {
			User user = (User) query.getSingleResult();
			logger.debug("User found: " + user);
			return user;
		} catch (Exception e) {
			logger.debug("User not found");
			return null;
		}
	}

	public User findByUsernameAndCompany(String username, String company) {
		Query query = em.createNamedQuery("User.findByUsernameAndCompany");
		query.setParameter("username", username);
		query.setParameter("company", company);
		try {
			return (User) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<User> findAllByCompany(String company) {
		Query query = em.createNamedQuery("User.findByCompany");
		query.setParameter("company", company);
		return query.getResultList();
	}




	public List<User> findAllUsersExcept(User user) {
		Query query = em.createNamedQuery("User.findAllExcept");
		query.setParameter("user", user);
		return query.getResultList();
	}

	public List<User> findAllEmailStatusEnabledByGroups(Collection<Group> groups) {
		Query query = em.createNamedQuery("User.findAllEmailStatusEnabledByGroups");
		query.setParameter("groups", groups);
		return query.getResultList();
	}

}
