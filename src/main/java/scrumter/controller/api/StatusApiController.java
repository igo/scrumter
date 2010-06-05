package scrumter.controller.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.entity.Comment;
import scrumter.model.entity.Group;
import scrumter.model.entity.Status;
import scrumter.model.entity.User;
import scrumter.service.GroupService;
import scrumter.service.SecurityService;
import scrumter.service.StatusService;

@Controller
public class StatusApiController {

	private Logger logger = Logger.getLogger(StatusApiController.class);

	@Autowired
	private StatusService statusService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/api/status/add", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional
	public ModelAndView addStatus(@RequestParam String status, @RequestParam List<Long> allowedGroups) {
		User currentUser = securityService.getCurrentUser();
		logger.info("Adding status '" + status + "' by " + currentUser.getEmail() + " for " + allowedGroups);
		if (allowedGroups.isEmpty()) {
			throw new IllegalArgumentException("Allowed groups list can't be empty");
		}
		Set<Group> statusGroups = new HashSet<Group>();
		for (Long allowedGroup : allowedGroups) {
			Group group = groupService.getGroupById(allowedGroup);
			// if current user is member of group
			if (group.getMembers().contains(currentUser)) {
				statusGroups.add(group);
			}
		}
		ModelAndView mav = new ModelAndView("status/detail");
		Status s = new Status(currentUser, status);
		s.setAllowedGroups(statusGroups);
		statusService.addStatus(s);
		mav.addObject("status", s);
		return mav;
	}

	@RequestMapping(value = "/api/status/add-comment", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView addComment(@RequestParam String comment, @RequestParam Long statusId) {
		logger.debug("Adding comment: " + statusId + "; " + comment);
		ModelAndView mav = new ModelAndView("status/comment/detail");
		Status status = statusService.getStatusById(statusId);
		User currentUser = securityService.getCurrentUser();
		Comment c = statusService.addComment(status, comment, currentUser);
		mav.addObject("comment", c);
		return mav;
	}

}
