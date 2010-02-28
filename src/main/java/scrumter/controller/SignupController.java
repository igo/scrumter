package scrumter.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import scrumter.model.SignupForm;
import scrumter.model.User;
import scrumter.service.UserService;


@Controller
public class SignupController {

	private Logger logger = Logger.getLogger(SignupController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String registrationForm(Model model) {
		model.addAttribute(new SignupForm());
		return "users/signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String registrationFormSubmit(@Valid SignupForm form, BindingResult result) {
		if (result.hasErrors()) {
			logger.debug("Signup form not valid: " + result.getAllErrors());
			return "users/signup";
		}
		logger.debug("User registered" + form);
		User user = form.getUser();
		logger.debug("User created: " + user);
		userService.addUser(user);
		return "redirect:/";
	}
	
}
