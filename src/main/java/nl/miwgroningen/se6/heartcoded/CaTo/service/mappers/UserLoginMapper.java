package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserLoginDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.springframework.stereotype.Component;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Maps the User to UserLoginDTO
 */

@Component
public class UserLoginMapper {

    public UserLoginDTO toDTO(User user) {
        UserLoginDTO result = new UserLoginDTO();
        result.setEmail(user.getEmail());
        result.setEmail(user.getEmail());
        return result;
    }
}

