package scrumter.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import scrumter.model.entity.User;

@Service
public class EmailService {

	private Logger logger = Logger.getLogger(EmailService.class);

	@Autowired
	private MailSender emailSender;

	@Autowired
	private SimpleMailMessage emailTemplate;
	
	@Autowired
	private LocalizationService localizationService;

	@Value(value = "#{config.url}")
	private String url;

	public void sendEmail(User[] users, String templateName, String... args) {
		List<String> list = Arrays.asList(args);
		list.add(0, url);
		String message = localizationService.getMessage("notification.email." + templateName + ".subject", args);
		logger.info("message: " + message);
		logger.info(emailSender);
		logger.info(emailTemplate.getFrom());
	}

	public void sendEmail(User[] users) {
		
	}
	
}
