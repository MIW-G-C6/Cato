package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.springframework.stereotype.Service;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * this converts User models to userDTO's
 */

@Service
public class UserDTOConverter {

    protected UserDTO toDTO(User user) {
        UserDTO result = new UserDTO();
        result.setUserId(user.getUserId());
        result.setName(user.getUsername());
        result.setEmail(user.getEmail());
        result.setGroupHasUsersList(user.getGroupHasUsersList());
        return result;
    }

    protected User toModel(UserDTO userDTO) {
        User result = new User();
        result.setUserId(userDTO.getUserId());
        result.setName(userDTO.getName());
        result.setEmail(userDTO.getEmail());
        userDTO.setGroupHasUsersList(userDTO.getGroupHasUsersList());

        return result;
    }
}
