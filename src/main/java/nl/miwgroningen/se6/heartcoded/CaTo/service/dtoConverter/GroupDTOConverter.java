package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupHasUsersDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 * Converts model Group to a DTO that the groupService requires
 */

@Service
public class GroupDTOConverter {

    private GroupHasUsersDTOConverter groupHasUsersDTOConverter;

    public GroupDTOConverter(GroupHasUsersDTOConverter groupHasUsersDTOConverter) {
        this.groupHasUsersDTOConverter = groupHasUsersDTOConverter;
    }

    public GroupDTO toDTO(Group group) {
        GroupDTO result = new GroupDTO();
        result.setGroupId(group.getGroupId());
        result.setGroupName(group.getGroupName());
        result.setGroupHasUsersDTOList(toDTOList(group.getGroupHasUsersList()));

        return result;
    }

    public Group toModel(GroupDTO groupDTO) {
        Group result = new Group();
        result.setGroupId(groupDTO.getGroupId());
        result.setGroupName(groupDTO.getGroupName());
        result.setGroupHasUsersList(toModelList(groupDTO.getGroupHasUsersDTOList()));

        return result;
    }

    private List<GroupHasUsersDTO> toDTOList(List<GroupHasUsers> groupHasUsersList) {
        List<GroupHasUsersDTO> groupHasUsersDTOList = new ArrayList<>();
        for (GroupHasUsers groupHasUsers : groupHasUsersList) {
            groupHasUsersDTOList.add(groupHasUsersDTOConverter.toDTO(groupHasUsers));
        }
        return groupHasUsersDTOList;
    }

    private List<GroupHasUsers> toModelList(List<GroupHasUsersDTO> groupHasUsersDTOList) {
        List<GroupHasUsers> groupHasUsersList = new ArrayList<>();
        for (GroupHasUsersDTO groupHasUsersDTO : groupHasUsersDTOList) {
            groupHasUsersList.add(groupHasUsersDTOConverter.toModel(groupHasUsersDTO));
        }
        return groupHasUsersList;
    }
}
