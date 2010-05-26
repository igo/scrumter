package scrumter.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scrumter.model.entity.Comment;
import scrumter.model.entity.Group;
import scrumter.model.entity.Status;
import scrumter.model.entity.User;
import scrumter.model.repository.StatusRepository;

@Service
public class StatusService {

	private Logger logger = Logger.getLogger(StatusService.class);

	@Autowired
	private StatusRepository statusRepository;

	public static final Integer STATUSES_PER_PAGE = 15;

	@Autowired
	private NotificationService notificationService;

	public void addStatus(Status status) {
		status.setCreated(new Date());
		statusRepository.create(status);
	}

	public void updateStatus(Status status) {
		statusRepository.update(status);
	}

	public Status getStatusById(Long id) {
		return statusRepository.findById(id);
	}

	public Comment addComment(Status status, String comment, User author) {
		Comment c = new Comment(author, comment);
		status.getComments().add(c);
		updateStatus(status);
		notificationService.addCommentNotification(status, author);
		return c;
	}

	public List<Status> getAllowedStatuses(User user, int page) {
		return statusRepository.findAllAllowed(user, (page - 1) * STATUSES_PER_PAGE, STATUSES_PER_PAGE);
	}

	public List<Status> getStatusesInGroup(Group group, int page) {
		return statusRepository.findAllByGroup(group, (page - 1) * STATUSES_PER_PAGE, STATUSES_PER_PAGE);
	}

	public List<Status> getAllowedStatusesFromUser(User user, User author, int page) {
		return statusRepository.findAllAllowedByAuthor(user, author, (page - 1) * STATUSES_PER_PAGE, STATUSES_PER_PAGE);
	}

	public Long getStatusCountInGroup(Group group) {
		return statusRepository.countInGroup(group);
	}

}
