package scrumter.controller.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.ajax.AjaxResponse;
import scrumter.model.entity.User;
import scrumter.service.LocalizationService;
import scrumter.service.SecurityService;
import scrumter.service.UserService;

@Controller
public class ProfileApiController {

	private Logger logger = Logger.getLogger(ProfileApiController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private LocalizationService localizationService;

	@RequestMapping(value = "/api/profile/email-notifications", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional
	public ModelMap changeEmailNotifications(@RequestParam(defaultValue = "false") boolean emailGroupMembershipChange,
			@RequestParam(defaultValue = "false") boolean emailStatus, @RequestParam(defaultValue = "false") boolean emailCommentOnStatus,
			@RequestParam(defaultValue = "false") boolean emailCommentOnOwnStatus, @RequestParam(defaultValue = "false") boolean emailCommentOnTouchedStatus) {
		logger.info("Changing email notifications");
		ModelMap model = new ModelMap();
		User user = securityService.getCurrentUser();
		userService.changeEmailNotifications(user, emailGroupMembershipChange, emailStatus, emailCommentOnStatus, emailCommentOnOwnStatus,
				emailCommentOnTouchedStatus);
		model.addAttribute("success", true);
		model.addAttribute("message", localizationService.getMessage("user.profile.emailNotifications.changed"));
		return model;
	}

	@RequestMapping(value = "/api/profile/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional
	public ModelAndView changePassword(@RequestParam String currentPassword, @RequestParam String newPassword) {
		AjaxResponse response = new AjaxResponse();
		User user = securityService.getCurrentUser();
		if (userService.checkPassword(user, currentPassword)) {
			logger.info("Changing password of " + user);
			userService.changePassword(user, newPassword);
			response.setSuccess(true);
			response.setMessage(localizationService.getMessage("user.profile.changePassword.passwordChanged"));
		} else {
			response.setSuccess(false);
			response.setMessage(localizationService.getMessage("user.profile.changePassword.incorrectPassword"));
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("response", response);
		return mav;
	}

	@RequestMapping(value = "/api/profile/picture", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public String setPicture(@RequestParam MultipartFile picture) {
		User user = securityService.getCurrentUser();
		logger.info("Saving picture for " + user);
		logger.debug("Picture params: " + picture);
		try {
			userService.changePicture(user, picture.getBytes());
		} catch (IOException e) {
			logger.error("Cannot save picture for " + user, e);
		}
		return "redirect:/profile";
	}

	@RequestMapping(value = "/api/profile/picture")
	@PreAuthorize("hasRole('ROLE_USER')")
	public void getPicture(@RequestParam String company, @RequestParam String username, OutputStream outputStream, HttpServletRequest request) {
		User user = userService.findUserByUsernameAndCompany(username, company);
		try {
			if (user.getPicture() != null) {
				outputStream.write(user.getPicture());
			} else {
				InputStream stream = request.getSession().getServletContext().getResourceAsStream("/WEB-INF/resources/images/user-male.png");
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
