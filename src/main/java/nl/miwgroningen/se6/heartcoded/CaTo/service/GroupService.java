package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the group repository and gives it to the controllers
 */

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    public void deleteGroupById(Integer groupId) {
        groupRepository.deleteById(groupId);
    }

    public void saveGroup (Group group) {
        groupRepository.save(group);
    }
}
