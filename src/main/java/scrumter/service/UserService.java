package scrumter.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scrumter.controller.InitController;
import scrumter.model.Authority;
import scrumter.model.Status;
import scrumter.model.User;


@Service
public class UserService {
	
	private Logger logger = Logger.getLogger(UserService.class);

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void addUser(User user) {
		user.setCreated(new Date());
		logger.info("Adding user: " + user);
		em.persist(user);
	}
	
	@Transactional
	public void saveUser(User user) {
		logger.info("Saving user: " + user);
		em.merge(user);
	}
	
	
	public List<User> findAllUsersExcept(User user) {
		Query query = em.createNamedQuery("User.findAllExcept");
		query.setParameter("user", user);
		return query.getResultList();
	}
	
	public List<User> findAllUsersByCompany(String company) {
		Query query = em.createNamedQuery("User.findByCompany");
		query.setParameter("company", company);
		return query.getResultList();
	}
	
	
	public User findUserByUsernameAndCompany(String username, String company) {
		Query query = em.createNamedQuery("User.findByUsernameAndCompany");
		query.setParameter("username", username);
		query.setParameter("company", company);
		try {
			return (User) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/*
	public List<Status> findStatusesByAuthor(User author) {
		Query query = em.createNamedQuery("Status.findAllByAuthor");
		query.setParameter("author", author);
		return query.getResultList();
	}*/
	
	@Transactional
	public void deleteAllUsers() {
		Query query = em.createNamedQuery("User.deleteAll");
		query.executeUpdate();
	}

	public User findUserByEmail(String email) {
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
	
	@Transactional
	public Authority createAuthority(String name) {
		Authority authority = new Authority(name);
		em.persist(authority);
		return authority;
	}
	
}
