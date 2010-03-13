package scrumter.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.Status;
import scrumter.model.SignupForm;
import scrumter.model.User;
import scrumter.service.StatusService;
import scrumter.service.SecurityService;


@Controller
public class HomeController {

	private Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	private StatusService statusService;

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
			List<Status> statuses = statusService.findFollowedStatuses(currentUser, null, 15);
			//logger.debug("Statuses: " + statuses);
			mav.addObject("statuses", statuses);
			
			//logger.debug("Comments: " + statusService.findStatusById(new Long(1)).getComments());
			
		}
		return mav;
	}
}
