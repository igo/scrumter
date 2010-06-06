package scrumter.model.converter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import scrumter.model.entity.Group;
import scrumter.model.repository.GroupRepository;

public class StringToGroupConverter implements Converter<String, Group> {

	private Logger logger = Logger.getLogger(StringToGroupConverter.class);

	@Autowired
	private GroupRepository groupRepository;

	@Override
	public Group convert(String source) {
		logger.debug("Converting id " + source + " to Group");
		return groupRepository.findById(Long.parseLong(source));
	}

}
