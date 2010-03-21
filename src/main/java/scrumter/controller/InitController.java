package scrumter.controller;

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
import scrumter.model.Status;
import scrumter.model.User;
import scrumter.service.StatusService;
import scrumter.service.UserService;


@Controller
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class InitController {

	private Logger logger = Logger.getLogger(InitController.class);

	@Autowired
	private StatusService statusService;

	@Autowired
	private UserService userService;

	@Transactional
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public void init() {
		logger.info("Init executed");

		logger.info("Deleting database");
		statusService.deleteAllStatuses();
		userService.deleteAllUsers();

		logger.info("Creating test data");
		Authority adminRole = userService.createAuthority("ROLE_ADMIN");
		Authority userRole = userService.createAuthority("ROLE_USER");
		
		User user = new User();
		user.setEmail("bruce.willis@holywood.com");
		user.setFirstName("Bruce");
		user.setLastName("Willis");
		user.setPassword("password");
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
		
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void reloadConfig() {
		logger.debug("Running tests...");
		User user = userService.findUserByEmail("bruce.willis@holywood.com");
		logger.debug("User found by email bruce.willis@holywood.com: " + user);
	}

}
