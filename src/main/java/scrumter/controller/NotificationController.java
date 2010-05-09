package scrumter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.User;
import scrumter.service.NotificationService;
import scrumter.service.SecurityService;

@Controller
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/api/notification/dismiss-all", method = RequestMethod.POST)
	public ModelAndView dismissAll() {
		User currentUser = securityService.getCurrentUser();
		notificationService.dismissAllForUser(currentUser);
		ModelAndView mav = new ModelAndView();
		mav.addObject("success", true);
		return mav;
	}

}
