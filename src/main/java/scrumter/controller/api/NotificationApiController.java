package scrumter.controller.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.entity.Notification;
import scrumter.model.entity.User;
import scrumter.service.NotificationService;
import scrumter.service.SecurityService;

@Controller
public class NotificationApiController {

	private Logger logger = Logger.getLogger(NotificationApiController.class);

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/api/notification/dismiss", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER') AND #notification.owner.email == principal.username")
	@Transactional
	public ModelAndView dismiss(@RequestParam("id") Notification notification) {
		logger.info("Dismissing notification: " + notification);
		notificationService.dismissNotification(notification);
		ModelAndView mav = new ModelAndView();
		mav.addObject("success", true);
		return mav;
	}

	@RequestMapping(value = "/api/notification/dismiss-all", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView dismissAll() {
		User currentUser = securityService.getCurrentUser();
		logger.info("Dismissing all notification for user " + currentUser);
		notificationService.dismissNotifications(currentUser);
		ModelAndView mav = new ModelAndView();
		mav.addObject("success", true);
		return mav;
	}

}
