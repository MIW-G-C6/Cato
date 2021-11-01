package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupHasUsersDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * This changes the model attributes to DTO attributes
 */

public class GroupHasUsersDTOConverter {

    public GroupHasUsersDTO convertToGroupHasUsersDTO (GroupHasUsers groupHasUsers) {
        GroupHasUsersDTO groupHasUsersDTO = new GroupHasUsersDTO();
        groupHasUsersDTO.setGroup(groupHasUsers.getGroup());
        groupHasUsersDTO.setUser(groupHasUsers.getUser());
        groupHasUsersDTO.setUserRole(groupHasUsers.getUserRole());
        groupHasUsersDTO.setAdmin(groupHasUsers.isAdmin());

        return groupHasUsersDTO;
    }



}
