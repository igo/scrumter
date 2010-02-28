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
import scrumter.model.Message;
import scrumter.model.User;
import scrumter.service.MessageService;
import scrumter.service.UserService;


@Controller
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class InitController {

	private Logger logger = Logger.getLogger(InitController.class);

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@Transactional
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public void init() {
		logger.info("Init executed");

		logger.info("Deleting database");
		messageService.deleteAllMessages();
		userService.deleteAllUsers();

		logger.info("Creating test data");
		Authority userRole = userService.createAuthority("ROLE_USER");
		
		
		User user = new User();
		user.setEmail("bruce.willis@holywood.com");
		user.setFirstName("Bruce");
		user.setLastName("Willis");
		user.setPassword("password");
		user.grantRole(userRole);
		userService.addUser(user);

		Message message = new Message();
		message.setMessage("Blah blah");
		message.setAuthor(user);
		messageService.addMessage(message);

		User user1 = new User();
		user1.setEmail("george.bush@whitehouse.com");
		user1.setFirstName("George");
		user1.setLastName("Bush");
		user1.setPassword("password");
		userService.addUser(user1);

		Message message1 = new Message();
		message1.setMessage("George said");
		message1.setAuthor(user1);
		messageService.addMessage(message1);


		User user2 = new User();
		user2.setEmail("barack.obama@whitehouse.com");
		user2.setFirstName("Barack");
		user2.setLastName("Obama");
		user2.setPassword("password");
		userService.addUser(user2);

		Message message2 = new Message();
		message2.setMessage("Barack said");
		message2.setAuthor(user2);
		messageService.addMessage(message2);
	}
	
	@RequestMapping(value = "/reload", method = RequestMethod.GET)
	public void reloadConfig() {
		
	}

}
