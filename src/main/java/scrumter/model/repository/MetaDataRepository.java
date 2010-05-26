package scrumter.model.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.entity.MetaData;

@Repository
public class MetaDataRepository {

	private Logger logger = Logger.getLogger(MetaDataRepository.class);

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void create(MetaData metaData) {
		logger.debug("Creating meta data: " + metaData);
		em.persist(metaData);
	}

	@Transactional
	public void delete(MetaData metaData) {
		logger.debug("Deleting meta data: " + metaData);
		em.remove(metaData);
	}

}
