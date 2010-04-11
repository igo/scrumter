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
import scrumter.model.Group.GroupType;
import scrumter.service.GroupService;
import scrumter.service.SecurityService;

public class GroupsCountPreparer  extends ViewPreparerSupport {
	
	private Logger logger = Logger.getLogger(GroupsCountPreparer.class);
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private SecurityService securityService;
	
	@Override
	@Transactional(readOnly=true)
	public void execute(TilesRequestContext tilesContext,
			AttributeContext attributeContext) throws PreparerException {
		logger.debug("Searching current user and groups count");
		User currentUser = securityService.getCurrentUser();
		attributeContext.putAttribute("currentUser", new Attribute(currentUser));
		Long publicGroups = groupService.countGroupsByTypeAndUser(GroupType.PUBLIC, currentUser);
		attributeContext.putAttribute("publicGroups", new Attribute(publicGroups));
		Long projectGroups = groupService.countGroupsByTypeAndUser(GroupType.PROJECT, currentUser);
		attributeContext.putAttribute("projectGroups", new Attribute(projectGroups));
	}


}
