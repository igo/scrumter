package scrumter.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.User;
import scrumter.service.SecurityService;
import scrumter.service.StatusService;
import scrumter.service.UserService;

@Controller
public class ProfileController {

	private Logger logger = Logger.getLogger(ProfileController.class);

	@Autowired
	private StatusService statusService;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/profile")
	@PreAuthorize("hasRole('ROLE_USER')")
	ModelAndView showGeneralInformation() {
		logger.info("Showing profile");
		ModelAndView mav = new ModelAndView("profile/general-info");
		User user = securityService.getCurrentUser();
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/profile/profile-picture")
	@PreAuthorize("hasRole('ROLE_USER')")
	ModelAndView showProfilePicture() {
		logger.info("Showing profile picture");
		ModelAndView mav = new ModelAndView("profile/profile-picture");
		User user = securityService.getCurrentUser();
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/profile/change-password")
	@PreAuthorize("hasRole('ROLE_USER')")
	ModelAndView showChangePassword() {
		logger.info("Showing profile picture");
		ModelAndView mav = new ModelAndView("profile/change-password");
		User user = securityService.getCurrentUser();
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/api/profile/picture", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public String setPicture(@RequestParam MultipartFile picture) {
		User user = securityService.getCurrentUser();
		logger.info("Saving picture for " + user);
		logger.debug("Picture params: " + picture);
		try {
			user.setPicture(picture.getBytes());
		} catch (IOException e) {
			logger.error("Cannot save picture for " + user, e);
		}
		userService.saveUser(user);
		return "redirect:/profile";
	}

	@RequestMapping(value = "/api/profile/picture")
	@PreAuthorize("hasRole('ROLE_USER')")
	public void getPicture(@RequestParam String company, @RequestParam String username, OutputStream outputStream,
			HttpServletRequest request) {
		User user = userService.findUserByUsernameAndCompany(username, company);
		try {
			if (user.getPicture() != null) {
				outputStream.write(user.getPicture());
			} else {
				InputStream stream = request.getSession().getServletContext().getResourceAsStream(
						"/WEB-INF/resources/images/user-male.png");
				// InputStream stream =
				// getClass().getResourceAsStream("/WEB-INF/resources/images/user-male.png");
				logger.debug("Default image: " + stream);
				int nextChar;
				while ((nextChar = stream.read()) != -1) {
					outputStream.write(nextChar);
				}
			}
		} catch (IOException e) {
			logger.error("Cannot generate picture for " + user, e);
		}
	}

}
