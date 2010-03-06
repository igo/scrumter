package scrumter.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.Status;
import scrumter.model.User;
import scrumter.service.StatusService;
import scrumter.service.SecurityService;
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
	
	@RequestMapping(value = "/users")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView listUsers() {
		logger.debug("View all users");
		ModelAndView mav = new ModelAndView("users/list");
		List<User> users = userService.findAllUsersExcept(securityService.getCurrentUser());
		mav.addObject("users", users);
		return mav;
	}

	@RequestMapping(value = "/{company}/{username}")
	@PreAuthorize("hasRole('ROLE_USER')")
	ModelAndView showUser(@PathVariable String company, @PathVariable String username) {
		ModelAndView mav = new ModelAndView("users/view");
		User user = userService.findUserByUsernameAndCompany(username, company);
		mav.addObject("user", user);
		logger.info("Showing user: " + user);
		List<Status> statuses = statusService.findStatusesByAuthor(user, 0, 3);
		mav.addObject("statuses", statuses);
		return mav;
	}

}
