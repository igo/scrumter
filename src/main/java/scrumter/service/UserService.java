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
import scrumter.model.Message;
import scrumter.model.User;


@Service
public class UserService {
	
	private Logger logger = Logger.getLogger(UserService.class);

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void addUser(User user) {
		user.setCreated(new Date());
		logger.info("Saving user: " + user);
		em.persist(user);
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
	public List<Message> findMessagesByAuthor(User author) {
		Query query = em.createNamedQuery("Message.findAllByAuthor");
		query.setParameter("author", author);
		return query.getResultList();
	}*/
	
	@Transactional
	public void deleteAllUsers() {
		Query query = em.createNamedQuery("User.deleteAll");
		query.executeUpdate();
	}

	public User findUserByEmail(String email) {
		Query query = em.createNamedQuery("User.findByEmail");
		query.setParameter("email", email);
		try {
			return (User) query.getSingleResult();
		} catch (Exception e) {
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
