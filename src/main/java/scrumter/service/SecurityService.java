package scrumter.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import scrumter.model.User;


@Service
public class SecurityService {

	private Logger logger = Logger.getLogger(SecurityService.class);

	@Autowired
	private UserService userService;

	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null) {
			logger.debug("Current logged in user is unknown");
		} else {
			if (principal instanceof org.springframework.security.core.userdetails.User) {
				org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				User user = userService.findUserByEmail(springUser.getUsername());
				logger.debug("Current logged in user: " + user);
				return user;
			} else if (principal instanceof String && "anonymousUser".equals(principal)) {
				logger.debug("Current logged in user: anonymousUser");
			} else {
				logger.debug("Unknown type of principal: " + principal.getClass().getName() + "; " + principal);
			}
		}
		return null;

	}

}
