package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import org.springframework.stereotype.Service;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 * Converts model Group to a DTO that the groupService requires
 */

@Service
public class GroupDTOConverter {

    public GroupDTO toDTO(Group group) {
        GroupDTO result = new GroupDTO();
        result.setGroupId(group.getGroupId());
        result.setGroupName(group.getGroupName());
        result.setGroupHasUsersDTOList(group.getGroupHasUsersList());

        return result;
    }

    public Group toModel(GroupDTO groupDTO) {
        Group result = new Group();
        result.setGroupId(groupDTO.getGroupId());
        result.setGroupName(groupDTO.getGroupName());
        result.setGroupHasUsersList(groupDTO.getGroupHasUsersDTOList());

        return result;
    }
}
