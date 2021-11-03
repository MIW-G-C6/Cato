package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import org.springframework.stereotype.Component;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Maps GroupDTO to Group entities and back
 */

@Component
public class GroupMapper {

    public GroupDTO toDTO(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupId(group.getGroupId());
        groupDTO.setGroupName(group.getGroupName());

        return groupDTO;
    }

    public Group toGroup(GroupDTO groupDTO) {
        Group group = new Group();
        group.setGroupId(groupDTO.getGroupId());
        group.setGroupName(groupDTO.getGroupName());

        return group;
    }
}

