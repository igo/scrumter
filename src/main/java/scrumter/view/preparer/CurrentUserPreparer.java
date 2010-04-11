package scrumter.view.preparer;

import org.apache.log4j.Logger;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.User;
import scrumter.service.SecurityService;

public class CurrentUserPreparer extends ViewPreparerSupport {

	private Logger logger = Logger.getLogger(CurrentUserPreparer.class);

	@Autowired
	private SecurityService securityService;

	@Override
	@Transactional(readOnly = true)
	public void execute(TilesRequestContext tilesContext,
			AttributeContext attributeContext) throws PreparerException {
		logger.debug("Searching current user");
		User user = securityService.getCurrentUser();
		attributeContext.putAttribute("currentUser", new Attribute(user));
	}
}
