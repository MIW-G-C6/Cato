package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserWithPasswordDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.springframework.stereotype.Service;


/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * this converts User models to userDTO's
 */

@Service
public class UserDTOConverter {

    private GroupHasUsersDTOConverter groupHasUsersDTOConverter;

    public UserDTOConverter(GroupHasUsersDTOConverter groupHasUsersDTOConverter) {
        this.groupHasUsersDTOConverter = groupHasUsersDTOConverter;
    }

    public UserDTO toDTO(User user) {
        UserDTO result = new UserDTO();
        result.setUserId(user.getUserId());
        result.setName(user.getUsername());
        result.setEmail(user.getEmail());
        result.setGroupHasUsersDTOList(groupHasUsersDTOConverter.toDTOList(user.getGroupHasUsersList()));

        return result;
    }

    public User toModel(UserDTO userDTO) {
        User result = new User();
        result.setUserId(userDTO.getUserId());
        result.setName(userDTO.getName());
        result.setEmail(userDTO.getEmail());
        result.setGroupHasUsersList(groupHasUsersDTOConverter.toModelList(userDTO.getGroupHasUsersDTOList()));

        return result;
    }

    public UserWithPasswordDTO toDTOWithPassword(User user) {
        UserWithPasswordDTO result = new UserWithPasswordDTO();
        result.setUserId(user.getUserId());
        result.setName(user.getUsername());
        result.setEmail(user.getEmail());
        result.setPassword(user.getPassword());
        result.setGroupHasUsersDTOList(groupHasUsersDTOConverter.toDTOList(user.getGroupHasUsersList()));

        return result;
    }

    public User toModelWithPassword(UserWithPasswordDTO userWithPasswordDTO) {
        User result = new User();
        result.setUserId(userWithPasswordDTO.getUserId());
        result.setName(userWithPasswordDTO.getName());
        result.setPassword(userWithPasswordDTO.getPassword());
        result.setEmail(userWithPasswordDTO.getEmail());
        result.setGroupHasUsersList(groupHasUsersDTOConverter.toModelList(userWithPasswordDTO.getGroupHasUsersDTOList()));

        return result;
    }
}
