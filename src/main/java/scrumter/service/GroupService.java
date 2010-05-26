package scrumter.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scrumter.model.entity.Group;
import scrumter.model.entity.User;
import scrumter.model.entity.Group.GroupType;
import scrumter.model.repository.GroupRepository;

@Service
public class GroupService {

	private Logger logger = Logger.getLogger(GroupService.class);

	@Autowired
	private GroupRepository groupRepository;

	public void addGroup(Group group) {
		groupRepository.create(group);
	}

	public void updateGroup(Group group) {
		groupRepository.update(group);
	}

	public Group getGroupById(Long id) {
		return groupRepository.findById(id);
	}

	public Group getGroupByLink(String link) {
		return groupRepository.findByLink(link);
	}

	public List<Group> getGroups() {
		return groupRepository.findAll();
	}

	public List<Group> getGroupsForUser(User user, GroupType groupType) {
		return groupRepository.findAllByMemberAndType(user, groupType);
	}

	public Long getGroupMembersCount(Group group) {
		return groupRepository.countMembers(group);
	}

}
