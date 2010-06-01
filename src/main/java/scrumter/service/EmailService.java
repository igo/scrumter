package scrumter.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
	private LocalizationService localizationService;

	@Value(value = "#{config['url']}")
	private String url;

	@Value(value = "#{config['email.from']}")
	private String from;

	@Value(value = "#{config['email.enabled']}")
	private boolean emailEnabled;

	public void sendEmailFromTemplate(User to, String templateName, String... args) {
		List<User> users = Arrays.asList(to);
		sendEmailsFromTemplate(users, templateName, args);
	}

	public void sendEmailsFromTemplate(Collection<User> to, String templateName, String... args) {
		logger.info("Sending email from template " + templateName + " to " + to);
		List<String> tmpList = Arrays.asList(args);
		List<String> list = new ArrayList<String>(tmpList);
		list.add(0, url);
		Object[] argsWithUrl = list.toArray();
		String subject = localizationService.getMessage("email." + templateName + ".subject", argsWithUrl);
		String message = localizationService.getMessage("email." + templateName + ".message", argsWithUrl);
		logger.debug(subject + "\n\n" + message);
		message = localizationService.getMessage("email.template", url, message);
		sendEmails(to, subject, message);
	}

	private void sendEmails(Collection<User> to, String subject, String message) {
		for (User user : to) {
			sendEmail(user.getEmail(), subject, message);
		}
	}

	private void sendEmail(String to, String subject, String message) {
		if (emailEnabled) {
			SimpleMailMessage emailTemplate = new SimpleMailMessage();
			emailTemplate.setFrom(from);
			emailTemplate.setSubject(subject);
			emailTemplate.setText(message);
			emailTemplate.setTo(to);
			logger.info("Sending e-mail to " + to);
			try {
				emailSender.send(emailTemplate);
			} catch (Exception e) {
				logger.error("Error during sending e-mail", e);
			}
		} else {
			logger.info("E-mail support disabled. Sending e-mail to " + to + " skipped");
		}
	}

}
