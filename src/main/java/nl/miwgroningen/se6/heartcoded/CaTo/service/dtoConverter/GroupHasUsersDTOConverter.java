package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupHasUsersDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * This changes the model attributes to DTO attributes
 */

@Service
public class GroupHasUsersDTOConverter {

    private final UserDTOConverter userDTOConverter;
    private final GroupDTOConverter groupDTOConverter;

    public GroupHasUsersDTOConverter(UserDTOConverter userDTOConverter, GroupDTOConverter groupDTOConverter) {
        this.userDTOConverter = userDTOConverter;
        this.groupDTOConverter = groupDTOConverter;
    }

    public GroupHasUsersDTO toDTO (GroupHasUsers groupHasUsers) {
        GroupHasUsersDTO result = new GroupHasUsersDTO();
        result.setGroup(groupDTOConverter.toDTO(groupHasUsers.getGroup()));
        result.setUser(userDTOConverter.toDTO(groupHasUsers.getUser()));
        result.setUserRole(groupHasUsers.getUserRole());
        result.setAdmin(groupHasUsers.isAdmin());

        return result;
    }

    public GroupHasUsers toModel (GroupHasUsersDTO groupHasUsersDTO) {
        GroupHasUsers result = new GroupHasUsers();
        result.setGroup(groupDTOConverter.toModel(groupHasUsersDTO.getGroup()));
        result.setUser(userDTOConverter.toModel(groupHasUsersDTO.getUser()));
        result.setUserRole(groupHasUsersDTO.getUserRole());
        result.setAdmin(groupHasUsersDTO.isAdmin());

        return result;
    }

    public List<GroupHasUsersDTO> toDTOList(List<GroupHasUsers> groupHasUsersList) {
        List<GroupHasUsersDTO> groupHasUsersDTOList = new ArrayList<>();
        for (GroupHasUsers groupHasUsers : groupHasUsersList) {
            groupHasUsersDTOList.add(toDTO(groupHasUsers));
        }
        return groupHasUsersDTOList;
    }

    public List<GroupHasUsers> toModelList(List<GroupHasUsersDTO> groupHasUsersDTOList) {
        List<GroupHasUsers> groupHasUsersList = new ArrayList<>();
        for (GroupHasUsersDTO groupHasUsersDTO : groupHasUsersDTOList) {
            groupHasUsersList.add(toModel(groupHasUsersDTO));
        }
        return groupHasUsersList;
    }

}
