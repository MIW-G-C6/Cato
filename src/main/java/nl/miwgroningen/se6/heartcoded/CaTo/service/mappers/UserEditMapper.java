package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserEditDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 * Maps User to UserEditDTO and reverse
 */
@Component
public class UserEditMapper {

    public UserEditDTO toDTO(User user) {
        UserEditDTO result = new UserEditDTO();
        result.setUserId(user.getUserId());
        result.setName(user.getName());
        result.setEmail(user.getEmail());
        return result;
    }

    public List<UserEditDTO> toDTO(List<User> userList) {
        return userList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<UserEditDTO> toDTO(Optional<User> user) {
        Optional<UserEditDTO> result = Optional.empty();
        if(user.isPresent()) {
            result = Optional.of(toDTO(user.get()));
        }
        return result;
    }

    public User toUser (UserEditDTO userEditDTO) {
        User result = new User();
        result.setUserId(userEditDTO.getUserId());
        result.setName(userEditDTO.getName());
        result.setEmail(userEditDTO.getEmail());
        return result;
    }
}
