package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<UserDTO> toDTO(List<User> userList) {
        return userList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<UserDTO> toDTO(Optional<User> user) {
        Optional<UserDTO> result = Optional.empty();
        if(user.isPresent()) {
            result = Optional.of(toDTO(user.get()));
        }
        return result;
    }

    public User toUser (UserDTO userDTO) {
        User result = new User();
        result.setUserId(userDTO.getUserId());
        result.setName(userDTO.getName());
        result.setEmail(userDTO.getEmail());
        return result;
    }
}
