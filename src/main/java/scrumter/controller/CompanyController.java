package scrumter.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.User;
import scrumter.service.UserService;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class CompanyController {

	private Logger logger = Logger.getLogger(CompanyController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{company}", method = RequestMethod.GET)
	public ModelAndView showCompany(@PathVariable String company) {
		ModelAndView mav = new ModelAndView("company");
		mav.addObject("company", company);
		List<User> users = userService.findAllUsersByCompany(company);
		mav.addObject("users", users);
		return mav;
	}

}
