package scrumter.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.entity.Status;
import scrumter.model.entity.User;
import scrumter.model.entity.Group.GroupType;
import scrumter.model.form.SignupForm;
import scrumter.service.GroupService;
import scrumter.service.StatusService;
import scrumter.service.SecurityService;


@Controller
public class HomeController {

	private Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	private GroupService groupService; 

	@Autowired
	private StatusService statusService;

	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(@RequestParam(defaultValue = "1") Integer page) {
		ModelAndView mav = new ModelAndView();
		User currentUser = securityService.getCurrentUser();
		if (currentUser == null) {
			mav.setViewName("login");
			mav.addObject(new SignupForm());
//			mav.setView(new RedirectView("/login", true));
		} else {
			mav.setViewName("users/home");
			List<Status> statuses = statusService.getAllowedStatuses(currentUser, page);
			mav.addObject("statuses", statuses);
			mav.addObject("page", page);
			mav.addObject("user", currentUser);
		}
		return mav;
	}
}
