package scrumter.model.converter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import scrumter.model.entity.Notification;
import scrumter.model.repository.NotificationRepository;

public class StringToNotificationConverter implements Converter<String, Notification> {

	private Logger logger = Logger.getLogger(StringToNotificationConverter.class);

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public Notification convert(String source) {
		logger.debug("Converting id " + source + " to Notification");
		return notificationRepository.findById(Long.parseLong(source));
	}

}
