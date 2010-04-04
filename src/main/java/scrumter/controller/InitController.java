package scrumter.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tiles.TilesApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.tiles2.TilesViewResolver;

import scrumter.model.Authority;
import scrumter.model.Comment;
import scrumter.model.Group;
import scrumter.model.Status;
import scrumter.model.User;
import scrumter.model.Group.GroupType;
import scrumter.service.GroupService;
import scrumter.service.StatusService;
import scrumter.service.UserService;

@Controller
// @PreAuthorize("hasRole('ROLE_ADMIN')")
public class InitController {

	private Logger logger = Logger.getLogger(InitController.class);

	@Autowired
	private StatusService statusService;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Transactional
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public void init() {
		logger.info("Init executed");

		logger.info("Deleting database");
		statusService.deleteAllStatuses();
		userService.deleteAllUsers();
		groupService.deleteAllGroups();

		logger.info("Creating test data");
		Authority adminRole = userService.createAuthority("ROLE_ADMIN");
		Authority userRole = userService.createAuthority("ROLE_USER");

		User user = new User();
		user.setEmail("bruce.willis@holywood.com");
		user.setFirstName("Bruce");
		user.setLastName("Willis");
		user.setPassword("password");
		user.grantRole(adminRole);
		user.grantRole(userRole);
		userService.addUser(user);
		logger.debug("User created: " + user);

		Status status = new Status();
		status.setStatus("Blah blah");
		status.setAuthor(user);
		statusService.addStatus(status);

		User user1 = new User();
		user1.setEmail("george.bush@whitehouse.com");
		user1.setFirstName("George");
		user1.setLastName("Bush");
		user1.setPassword("password");
		user1.grantRole(userRole);
		userService.addUser(user1);

		Status status1 = new Status();
		status1.setStatus("George said");
		status1.setAuthor(user1);
		statusService.addStatus(status1);

		User user2 = new User();
		user2.setEmail("barack.obama@whitehouse.com");
		user2.setFirstName("Barack");
		user2.setLastName("Obama");
		user2.setPassword("password");
		userService.addUser(user2);

		Status status2 = new Status();
		status2.setStatus("Barack said");
		status2.setAuthor(user2);
		Comment comment1 = new Comment(user2, "Blaaaaa");
		status2.getComments().add(comment1);
		statusService.addStatus(status2);

		Group group1 = new Group("Presidents", user1, GroupType.PROJECT);
		groupService.addGroup(group1);

		Group group2 = new Group("Friends", user1, GroupType.PUBLIC);
		groupService.addGroup(group2);

		user.addMembership(group1);
		user.addMembership(group2);
		userService.saveUser(user);
		user1.addMembership(group1);
		userService.saveUser(user1);
		user2.addMembership(group1);
		user2.addMembership(group2);
		userService.saveUser(user2);

	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public void reloadConfig() {
		logger.debug("Running tests...");
		User user = userService.findUserByEmail("bruce.willis@holywood.com");
		logger.debug("User found by email bruce.willis@holywood.com: " + user);

		List<Group> groups = groupService.findAllGroups();
		logger.debug(groups);
		user = userService.findUserByEmail("barack.obama@whitehouse.com");
		logger.debug("Membership: " + groupService.findUserMembership(user));
	}

}
