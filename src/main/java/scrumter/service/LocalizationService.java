package scrumter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class LocalizationService {

	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	public String getMessage(String code) {
		return messageSource.getMessage(code, new Object[0],
				LocaleContextHolder.getLocale());
	}

	public String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, LocaleContextHolder
				.getLocale());
	}

}
