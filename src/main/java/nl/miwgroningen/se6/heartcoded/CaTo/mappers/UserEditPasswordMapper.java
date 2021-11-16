package nl.miwgroningen.se6.heartcoded.CaTo.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserEditPasswordDTO;
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
public class UserEditPasswordMapper {

    public UserEditPasswordDTO toDTO(User user) {
        UserEditPasswordDTO result = new UserEditPasswordDTO();
        result.setUserId(user.getUserId());
        result.setName(user.getName());
        result.setEmail(user.getEmail());
        return result;
    }

    public List<UserEditPasswordDTO> toDTO(List<User> userList) {
        return userList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<UserEditPasswordDTO> toDTO(Optional<User> user) {
        Optional<UserEditPasswordDTO> result = Optional.empty();
        if(user.isPresent()) {
            result = Optional.of(toDTO(user.get()));
        }
        return result;
    }

    //TODO not necessary?
    public User toUser (UserEditPasswordDTO userEditPasswordDTO) {
        User result = new User();
        result.setUserId(userEditPasswordDTO.getUserId());
        result.setName(userEditPasswordDTO.getName());
        result.setEmail(userEditPasswordDTO.getEmail());
        return result;
    }
}
