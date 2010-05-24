package scrumter.model.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.entity.Authority;

@Repository
public class AuthorityRepository {

	private Logger logger = Logger.getLogger(AuthorityRepository.class);

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void create(Authority authority) {
		logger.info("Creating authority: " + authority);
		em.persist(authority);
	}

	@Transactional
	public void update(Authority authority) {
		logger.info("Updating authority: " + authority);
		em.merge(authority);
	}

}
