package scrumter.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import scrumter.model.Comment;
import scrumter.model.Status;
import scrumter.service.StatusService;
import scrumter.service.SecurityService;
import scrumter.service.UserService;

@Controller
public class StatusController {

	private Logger logger = Logger.getLogger(StatusController.class);

	@Autowired
	private StatusService statusService;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/api/status/add", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView addStatus(@RequestParam String status, @RequestParam(required = false) Long parendId) {
		ModelAndView mav = new ModelAndView("status/detail");
		Status s = new Status(securityService.getCurrentUser(), status);
		statusService.addStatus(s);
		mav.addObject("status", s);
		return mav;
	}

	@RequestMapping(value = "/api/status/list", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView listStatuses(@RequestParam String status, @RequestParam(required = false) Long parendId) {
		ModelAndView mav = new ModelAndView("statuses/detail");
		Status s = new Status(securityService.getCurrentUser(), status);
		statusService.addStatus(s);
		mav.addObject("status", s);
		return mav;
	}

	@RequestMapping(value = "/api/status/add-comment", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView addComment(@RequestParam String comment, @RequestParam Long statusId) {
		logger.debug("Adding comment: " + statusId + "; " + comment);
		ModelAndView mav = new ModelAndView("status/comment/detail");
		Status status  = statusService.findStatusById(statusId);
		Comment c = new Comment(securityService.getCurrentUser(), comment);
		status.getComments().add(c);
		statusService.saveStatus(status);
		mav.addObject("comment", c);
		return mav;
	}

	/*
	 * @RequestMapping(value = "/messages/data.json")
	 * 
	 * @PreAuthorize("hasRole('ROLE_USER')") ModelAndView listJson() {
	 * ModelAndView mav = new ModelAndView("message/list"); User user =
	 * userService.findUserByUsernameAndCompany("admin"); mav.addObject("user",
	 * user); logger.info(user); List<Message> messages =
	 * messageService.findMessagesByAuthor(user); mav.addObject("messages",
	 * messages); //em.createNamedQuery(""); return mav; }
	 */

}
