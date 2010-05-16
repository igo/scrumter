package scrumter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.Notification;
import scrumter.model.User;
import scrumter.service.NotificationService;
import scrumter.service.SecurityService;

@Controller
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/api/notification/dismiss", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional
	public ModelAndView dismiss(Long id) {
		User currentUser = securityService.getCurrentUser();
		Notification notification = notificationService.findNotificationById(id);
		if (!notification.getOwner().equals(currentUser)) {
			throw new AuthorizationServiceException("Not your notification!");
		}
		notificationService.dismiss(notification);
		ModelAndView mav = new ModelAndView();
		mav.addObject("success", true);
		return mav;
	}

	@RequestMapping(value = "/api/notification/dismiss-all", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView dismissAll() {
		User currentUser = securityService.getCurrentUser();
		notificationService.dismissAllForUser(currentUser);
		ModelAndView mav = new ModelAndView();
		mav.addObject("success", true);
		return mav;
	}

}
