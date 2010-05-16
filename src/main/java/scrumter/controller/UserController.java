package scrumter.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.Status;
import scrumter.model.User;
import scrumter.service.SecurityService;
import scrumter.service.StatusService;
import scrumter.service.UserService;

@Controller
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private StatusService statusService;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/{company}/{username}")
	@PreAuthorize("hasRole('ROLE_USER')")
	ModelAndView showUser(@PathVariable String company, @PathVariable String username,
			@RequestParam(defaultValue = "1") Integer page) {
		ModelAndView mav = new ModelAndView("users/view");
		User user = userService.findUserByUsernameAndCompany(username, company);
		mav.addObject("user", user);
		logger.info("Showing user: " + user);
		List<Status> statuses = statusService.findStatusesByAuthor(user, page);
		mav.addObject("statuses", statuses);
		mav.addObject("page", page);
		return mav;
	}

}
