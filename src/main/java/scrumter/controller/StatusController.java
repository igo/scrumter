package scrumter.controller;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.entity.Group;
import scrumter.model.entity.Status;
import scrumter.model.entity.User;
import scrumter.service.SecurityService;
import scrumter.service.StatusService;

@Controller
public class StatusController {

	private Logger logger = Logger.getLogger(StatusController.class);

	@Autowired
	private StatusService statusService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/{company}/{username}/statuses/{statusId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional(readOnly = true)
	public ModelAndView showStatus(@PathVariable String company, @PathVariable String username, @PathVariable Long statusId) {
		logger.info("Showing status id=" + statusId);
		User currentUser = securityService.getCurrentUser();
		Status status = statusService.getStatusById(statusId);
		// check if current user is allowed to view status
		Set<Group> allowedGroups = new HashSet<Group>(status.getAllowedGroups());
		int startSize = allowedGroups.size();
		allowedGroups.removeAll(currentUser.getMembership());
		int endSize = allowedGroups.size();
		if (startSize == endSize) {
			throw new AuthorizationServiceException("Not allowed to view this status!");
		}
		ModelAndView mav = new ModelAndView("status/view");
		mav.addObject("status", status);
		return mav;
	}

}
