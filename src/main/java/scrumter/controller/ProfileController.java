package scrumter.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.entity.User;
import scrumter.service.SecurityService;

@Controller
public class ProfileController {

	private Logger logger = Logger.getLogger(ProfileController.class);

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/profile")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView showGeneralInformation() {
		logger.info("Showing profile");
		ModelAndView mav = new ModelAndView("profile/general-info");
		User user = securityService.getCurrentUser();
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/profile/profile-picture")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView showProfilePicture() {
		logger.info("Showing profile picture");
		ModelAndView mav = new ModelAndView("profile/profile-picture");
		User user = securityService.getCurrentUser();
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/profile/change-password")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView showChangePassword() {
		logger.info("Showing change password");
		ModelAndView mav = new ModelAndView("profile/change-password");
		User user = securityService.getCurrentUser();
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/profile/email-notifications")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView showEmailNotifications() {
		logger.info("Showing email notifications");
		ModelAndView mav = new ModelAndView("profile/email-notifications");
		User user = securityService.getCurrentUser();
		mav.addObject("user", user);
		return mav;
	}

}
