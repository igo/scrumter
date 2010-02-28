package scrumter.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scrumter.controller.MessageController;
import scrumter.model.Message;
import scrumter.model.User;


@Service
public class MessageService {
	
	private Logger logger = Logger.getLogger(MessageService.class);


	@PersistenceContext
	EntityManager em;

	@Transactional
	public void addMessage(Message message) {
		logger.debug("Saving message: " + message);
		message.setCreated(new Date());
		em.persist(message);
		em.flush();
	}
	
	public List<Message> findMessagesByAuthor(User author, Integer startPosition, Integer maxResult) {
		Query query = em.createNamedQuery("Message.findAllByAuthor");
		query.setParameter("author", author);
		if (startPosition != null) {
			query.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			query.setMaxResults(maxResult);
		}
		return query.getResultList();
	}
	
	public List<Message> findFollowedMessages(User follower) {
		Query query = em.createNamedQuery("Message.findAll");
//		query.setParameter("author", author);
		return query.getResultList();
	}
	
	@Transactional
	public void deleteAllMessages() {
		Query query = em.createNamedQuery("Message.deleteAll");
		query.executeUpdate();
	}
	
}
