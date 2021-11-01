package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupHasUsersDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import org.springframework.stereotype.Service;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * This changes the model attributes to DTO attributes
 */

@Service
public class GroupHasUsersDTOConverter {

    UserDTOConverter userDTOConverter;
    GroupDTOConverter groupDTOConverter;

    public GroupHasUsersDTOConverter(UserDTOConverter userDTOConverter, GroupDTOConverter groupDTOConverter) {
        this.userDTOConverter = userDTOConverter;
        this.groupDTOConverter = groupDTOConverter;
    }

    public GroupHasUsersDTO toDTO (GroupHasUsers groupHasUsers) {
        GroupHasUsersDTO groupHasUsersDTO = new GroupHasUsersDTO();
        groupHasUsersDTO.setGroup(groupDTOConverter.toDTO(groupHasUsers.getGroup()));
        groupHasUsersDTO.setUser(userDTOConverter.toDTO(groupHasUsers.getUser()));
        groupHasUsersDTO.setUserRole(groupHasUsers.getUserRole());
        groupHasUsersDTO.setAdmin(groupHasUsers.isAdmin());

        return groupHasUsersDTO;
    }



}
