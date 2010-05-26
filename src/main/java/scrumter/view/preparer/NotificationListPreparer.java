package scrumter.view.preparer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparerSupport;
import org.hibernate.jmx.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import scrumter.model.entity.MetaData;
import scrumter.model.entity.Notification;
import scrumter.model.entity.Status;
import scrumter.model.entity.User;
import scrumter.service.NotificationService;
import scrumter.service.SecurityService;
import scrumter.service.StatusService;
import scrumter.service.UserService;

public class NotificationListPreparer extends ViewPreparerSupport {

	private Logger logger = Logger.getLogger(NotificationListPreparer.class);

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StatusService statusService; 

	@Override
	@Transactional(readOnly = true)
	public void execute(TilesRequestContext tilesContext, AttributeContext attributeContext) throws PreparerException {
		logger.debug("Searching current user and groups count");
		User currentUser = securityService.getCurrentUser();
		attributeContext.putAttribute("currentUser", new Attribute(currentUser));
		List<Notification> notifications = notificationService.getNotifications(currentUser);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Notification notification : notifications) {
			Set<MetaData> meta = notification.getMeta();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", notification.getId());
			params.put("type", notification.getType());
			for (MetaData metaData : meta) {
				if ("user".equals(metaData.getKey())) {
					User user = userService.findUserById(Long.parseLong(metaData.getValue()));
					params.put("user", user);
				} else if ("status".equals(metaData.getKey())) {
					Status status = statusService.getStatusById(Long.parseLong(metaData.getValue()));
					params.put("status", status);
				} else {
					params.put(metaData.getKey(), metaData.getValue());
				}
			}
			list.add(params);
		}
		attributeContext.putAttribute("notifications", new Attribute(list));
	}

}
