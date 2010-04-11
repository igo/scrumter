package scrumter.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.Group;
import scrumter.model.User;
import scrumter.service.GroupService;
import scrumter.service.SecurityService;
import scrumter.service.StatusService;
import scrumter.service.UserService;

@Controller
public class GroupController {

	private Logger logger = Logger.getLogger(GroupController.class);

	@Autowired
	private StatusService statusService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/{company}/{username}/projects")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional
	public ModelAndView zobrazPrj(@PathVariable String company, @PathVariable String username) {
		logger.info("Zobraz prj: " + company + username);
		ModelAndView mav = new ModelAndView("group/list");
		User user = userService.findUserByUsernameAndCompany(username, company);
		// lazy load membership
		user.getMembership().toString();
		List<Group> groups = groupService.findUserMembership(user);
		logger.info(groups);
		mav.addObject("user", user);
		mav.addObject("groups", groups);
		return mav;
	}

}
