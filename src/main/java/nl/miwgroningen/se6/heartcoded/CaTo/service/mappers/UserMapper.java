package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Maps User tot UserDTO
 */

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        UserDTO result = new UserDTO();
        result.setUserId(user.getUserId());
        result.setName(user.getName());
        result.setEmail(user.getEmail());
        return result;
    }


}
