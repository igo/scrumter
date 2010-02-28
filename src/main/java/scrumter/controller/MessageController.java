package scrumter.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.Message;
import scrumter.model.User;
import scrumter.service.MessageService;
import scrumter.service.SecurityService;
import scrumter.service.UserService;


@Controller
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class MessageController {
	
	private Logger logger = Logger.getLogger(MessageController.class);
	
	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityService securityService;


	@RequestMapping(value = "/api/messages/add", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView addMessage(@RequestParam String message, @RequestParam(required = false) Long parendId) {
		ModelAndView mav = new ModelAndView("messages/detail");
		Message msg = new Message(securityService.getCurrentUser(), message);
		messageService.addMessage(msg);
		mav.addObject("message", msg);
		return mav;
	}
	
	@RequestMapping(value = "/api/messages/list", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView listMessages(@RequestParam String message, @RequestParam(required = false) Long parendId) {
		ModelAndView mav = new ModelAndView("messages/detail");
		Message msg = new Message(securityService.getCurrentUser(), message);
		messageService.addMessage(msg);
		mav.addObject("message", msg);
		return mav;
	}
	
	
	/*
	@RequestMapping(value = "/messages/data.json")
	@PreAuthorize("hasRole('ROLE_USER')")
	ModelAndView listJson() {
		ModelAndView mav = new ModelAndView("message/list");
		User user = userService.findUserByUsernameAndCompany("admin");
		mav.addObject("user", user);
		logger.info(user);
		List<Message> messages = messageService.findMessagesByAuthor(user);
		mav.addObject("messages", messages);
		//em.createNamedQuery("");
		return mav;
	}
	*/
	
}
