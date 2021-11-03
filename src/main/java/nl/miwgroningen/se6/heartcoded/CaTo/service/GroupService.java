package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.GroupDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the group repository and gives it to the controllers
 */

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupDTOConverter groupDTOConverter;

    public GroupService(GroupRepository groupRepository, GroupDTOConverter groupDTOConverter) {
        this.groupRepository = groupRepository;
        this.groupDTOConverter = groupDTOConverter;
    }

    public List<GroupDTO> findAllGroups() {
        List<Group> allGroups = groupRepository.findAll();
        List<GroupDTO> result = new ArrayList<>();
        for (Group group : allGroups) {
            result.add(groupDTOConverter.toDTO(group));
        }
        return result;
    }

    public void deleteGroupById(Integer groupId) {
        groupRepository.deleteById(groupId);
    }

    public void saveGroup (GroupDTO groupDTO) {
        Group result = groupDTOConverter.toModel(groupDTO);
        groupRepository.save(result);
    }

    public GroupDTO getById(Integer groupId) {
        Group group = groupRepository.getById(groupId);
        GroupDTO result = groupDTOConverter.toDTO(group);
        return result;
    }
}
