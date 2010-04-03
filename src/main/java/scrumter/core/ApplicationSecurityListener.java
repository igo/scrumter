package scrumter.core;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

public class ApplicationSecurityListener implements ApplicationListener {
	private static final Logger logger = Logger
			.getLogger(ApplicationSecurityListener.class);

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AuthorizedEvent) {
			AuthorizedEvent authorizedEvent = (AuthorizedEvent) event;
			logger.debug("authorized:" + authorizedEvent);
		} else if (event instanceof AuthorizationFailureEvent) {
			AuthorizationFailureEvent authorizationFailureEvent = (AuthorizationFailureEvent) event;
			logger.debug("not authorized:" + authorizationFailureEvent);
		} else if (event instanceof AuthenticationFailureBadCredentialsEvent) {
			AuthenticationFailureBadCredentialsEvent badCredentialsEvent = (AuthenticationFailureBadCredentialsEvent) event;
			logger.debug("badCredentials:" + badCredentialsEvent);
		} else if (event instanceof AuthenticationSuccessEvent) {
			AuthenticationSuccessEvent authenticationSuccessEvent = (AuthenticationSuccessEvent) event;
			logger.debug("authSuccess:" + authenticationSuccessEvent);
		} else {
			logger.debug("undefined: " + event);
		}
	}
}