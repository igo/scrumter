package scrumter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.Message;
import scrumter.model.SignupForm;
import scrumter.model.User;
import scrumter.service.MessageService;
import scrumter.service.SecurityService;


@Controller
public class HomeController {
	
	@Autowired
	private MessageService messageService;

	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		User currentUser = securityService.getCurrentUser();
		if (currentUser == null) {
			mav.setViewName("login");
			mav.addObject(new SignupForm());
//			mav.setView(new RedirectView("/login", true));
		} else {
			mav.setViewName("users/home");
			List<Message> messages = messageService.findFollowedMessages(currentUser, null, 15);
			mav.addObject("messages", messages);
		}
		return mav;
	}
}
