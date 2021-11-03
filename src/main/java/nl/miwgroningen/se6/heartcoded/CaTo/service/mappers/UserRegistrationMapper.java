package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserRegistrationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.springframework.stereotype.Component;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Maps the User tot the UserRegistrationDTO and vice versa
 */

@Component
public class UserRegistrationMapper {

    public UserRegistrationDTO toDTO(User user) {
        UserRegistrationDTO result = new UserRegistrationDTO();
        result.setUserId(user.getUserId());
        result.setName(user.getName());
        result.setEmail(user.getEmail());
        result.setPassword(user.getPassword());

        return result;
    }

    public User toUser(UserRegistrationDTO userRegistrationDTO) {
        User result = new User();
        result.setName(userRegistrationDTO.getName());
        result.setPassword(userRegistrationDTO.getPassword());
        result.setEmail(userRegistrationDTO.getEmail());

        return result;
    }
}
