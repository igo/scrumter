package scrumter.view.preparer;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.Group;
import scrumter.model.User;
import scrumter.model.Group.GroupType;
import scrumter.service.GroupService;
import scrumter.service.SecurityService;

public class UserMembershipPreparer extends ViewPreparerSupport {
	
	private Logger logger = Logger.getLogger(UserMembershipPreparer.class);
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private SecurityService securityService;
	
	@Override
	@Transactional(readOnly=true)
	public void execute(TilesRequestContext tilesContext,
			AttributeContext attributeContext) throws PreparerException {
		logger.debug("Searching current user and membership");
		User user = securityService.getCurrentUser();
		List<Group> groups = groupService.findGroupsByMemberAndType(user, GroupType.PUBLIC);
		attributeContext.putAttribute("groups", new Attribute(groups));
		List<Group> projects = groupService.findGroupsByMemberAndType(user, GroupType.PROJECT);
		attributeContext.putAttribute("projects", new Attribute(projects));
		logger.debug(groups);
	}

}
