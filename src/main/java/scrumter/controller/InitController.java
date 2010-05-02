package scrumter.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import scrumter.model.Authority;
import scrumter.model.Comment;
import scrumter.model.Group;
import scrumter.model.MetaData;
import scrumter.model.Notification;
import scrumter.model.Status;
import scrumter.model.User;
import scrumter.model.Group.GroupType;
import scrumter.service.GroupService;
import scrumter.service.NotificationService;
import scrumter.service.StatusService;
import scrumter.service.StringUtils;
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
	
	@Autowired
	private NotificationService notificationService;

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

		User userBruce = new User();
		userBruce.setEmail("bruce.willis@holywood.com");
		userBruce.setFirstName("Bruce");
		userBruce.setLastName("Willis");
		userBruce.setPassword("password");
		userBruce.setPosition("Actor");
		userBruce.grantRole(adminRole);
		userBruce.grantRole(userRole);
		userService.addUser(userBruce);
		logger.debug("User created: " + userBruce);

		Status status = new Status();
		status.setStatus("Blah blah");
		status.setAuthor(userBruce);
		statusService.addStatus(status);

		User userBush = new User();
		userBush.setEmail("george.bush@whitehouse.com");
		userBush.setFirstName("George");
		userBush.setLastName("Bush");
		userBush.setPassword("password");
		userBush.setPosition("Used to be president");
		userBush.grantRole(userRole);
		userService.addUser(userBush);

		Status status1 = new Status();
		status1.setStatus("George said");
		status1.setAuthor(userBush);
		statusService.addStatus(status1);

		User userObama = new User();
		userObama.setEmail("barack.obama@whitehouse.com");
		userObama.setFirstName("Barack");
		userObama.setLastName("Obama");
		userObama.setPassword("password");
		userObama.setPosition("USA President, Member of Congress");
		userService.addUser(userObama);

		Status status2 = new Status();
		status2.setStatus("Barack said");
		status2.setAuthor(userObama);
		Comment comment1 = new Comment(userObama, "Blaaaaa");
		status2.getComments().add(comment1);
		statusService.addStatus(status2);

		Group presidentsGroup = new Group("US Presidents", StringUtils.stringToPrettyLink("US Presidents"), userBush, GroupType.PROJECT);
		presidentsGroup.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas consectetur ante sit amet neque pharetra egestas. Nunc leo quam, luctus eu venenatis vitae, suscipit et nisi.");
		groupService.addGroup(presidentsGroup);

		Group friendsGroup = new Group("My Friends", StringUtils.stringToPrettyLink("My Friends"), userBush, GroupType.PUBLIC);
		friendsGroup.setDescription("Cras lobortis enim eu nibh posuere vel congue massa pulvinar. Sed sollicitudin odio eu ante varius consequat. Nulla commodo lorem a tellus vehicula id tincidunt velit porta.");
		groupService.addGroup(friendsGroup);

		Group famousGroup = new Group("Famous people", StringUtils.stringToPrettyLink("Famous people"), userBruce, GroupType.PROJECT);
		groupService.addGroup(famousGroup);

		userBruce.addMembership(friendsGroup);
		userBruce.addMembership(famousGroup);
		userBruce.addMembership(presidentsGroup);
		userService.saveUser(userBruce);
		userBush.addMembership(presidentsGroup);
		userBush.addMembership(friendsGroup);
		userService.saveUser(userBush);
		userObama.addMembership(presidentsGroup);
		userObama.addMembership(friendsGroup);
		userObama.addMembership(famousGroup);
		userService.saveUser(userObama);
		
		Set<MetaData> metas = new HashSet<MetaData>();
		MetaData meta1Who = new MetaData("user", status1.getAuthor().getId().toString()); 
		MetaData meta1Comment = new MetaData("status", status1.getId().toString()); 
		metas.add(meta1Who);
		metas.add(meta1Comment);
		Notification notification1 = new Notification("comment", userBruce, metas);
		notificationService.addNotification(notification1);

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
		logger.debug("Membership: " + groupService.findGroupsByMember(user));
	}

}
