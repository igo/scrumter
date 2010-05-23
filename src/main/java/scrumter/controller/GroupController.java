package scrumter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.entity.Group;
import scrumter.model.entity.Status;
import scrumter.model.entity.User;
import scrumter.model.entity.Group.GroupType;
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
	
	private Map<Group, Map<String, String>> getGroupsStatistics(List<Group> groups) {
		Map<Group, Map<String, String>> out = new HashMap<Group, Map<String, String>>();
		for (Group group : groups) {
			Map<String, String> params = new HashMap<String, String>();
			logger.debug("Counting members");
			params.put("members", groupService.countMembers(group).toString());
			logger.debug("Counting statuses");
			params.put("statuses", statusService.countStatusesInGroup(group).toString());
			out.put(group, params);
		}
		return out;
	}

	@RequestMapping(value = "/groups")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView showGroups() {
		User user = securityService.getCurrentUser();
		logger.info("Show groups for " + user);
		ModelAndView mav = new ModelAndView("group/list");
		List<Group> groups = groupService.findGroupsByMemberAndType(user, GroupType.PUBLIC);
		mav.addObject("user", user);
		mav.addObject("groups", groups);
		mav.addObject("groupsStats", getGroupsStatistics(groups));
		return mav;
	}

	@RequestMapping(value = "/groups/{groupLink}")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional(readOnly = true)
	public ModelAndView showGroup(@PathVariable String groupLink, @RequestParam(defaultValue = "1") Integer page) {
		Group group = groupService.findGroupByLink(groupLink);
		User user = securityService.getCurrentUser();
		if (!user.getMembership().contains(group)) {
			throw new AuthorizationServiceException("Not a member of this group");
		}
		List<Status> statuses = statusService.findStatusesByGroup(group, page);
		ModelAndView mav = new ModelAndView("group/home");
		mav.addObject("group", group);
		mav.addObject("statuses", statuses);
		mav.addObject("page", page);
		return mav;
	}

	@RequestMapping(value = "/groups/{groupLink}/members")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional(readOnly = true)
	public ModelAndView showGroupMembers(@PathVariable String groupLink) {
		Group group = groupService.findGroupByLink(groupLink);
		// lazy load TODO: fix
		group.getMembers().toString();
		User user = securityService.getCurrentUser();
		if (!user.getMembership().contains(group)) {
			throw new AuthorizationServiceException("Not a member of this group");
		}
		ModelAndView mav = new ModelAndView("group/members");
		mav.addObject("user", user);
		mav.addObject("group", group);
		return mav;
	}

	@RequestMapping(value = "/projects")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView showProjects() {
		User user = securityService.getCurrentUser();
		logger.info("Show projects for " + user);
		ModelAndView mav = new ModelAndView("project/list");
		List<Group> groups = groupService.findGroupsByMemberAndType(user, GroupType.PROJECT);
		mav.addObject("user", user);
		mav.addObject("groups", groups);
		mav.addObject("groupsStats", getGroupsStatistics(groups));
		return mav;
	}

	@RequestMapping(value = "/projects/{groupLink}")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional(readOnly = true)
	public ModelAndView showProject(@PathVariable String groupLink, @RequestParam(defaultValue = "1") Integer page) {
		Group group = groupService.findGroupByLink(groupLink);
		User user = securityService.getCurrentUser();
		if (!user.getMembership().contains(group)) {
			throw new AuthorizationServiceException("Not a member of this group");
		}
		List<Status> statuses = statusService.findStatusesByGroup(group, page);
		logger.info("Statuses: " + statuses);
		ModelAndView mav = new ModelAndView("project/home");
		mav.addObject("group", group);
		mav.addObject("statuses", statuses);
		mav.addObject("page", page);
		return mav;
	}

	@RequestMapping(value = "/projects/{groupLink}/members")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional(readOnly = true)
	public ModelAndView showProjectMembers(@PathVariable String groupLink) {
		Group group = groupService.findGroupByLink(groupLink);
		// lazy load TODO: fix
		group.getMembers().toString();
		User user = securityService.getCurrentUser();
		if (!user.getMembership().contains(group)) {
			throw new AuthorizationServiceException("Not a member of this group");
		}
		ModelAndView mav = new ModelAndView("project/members");
		mav.addObject("user", user);
		mav.addObject("group", group);
		return mav;
	}

}
