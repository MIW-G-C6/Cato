package nl.miwgroningen.se6.heartcoded.CaTo.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.aspectj.bridge.MessageUtil.fail;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Maps User tot UserDTO and back.
 */

@Component
public class UserMapper {

    private static final String DEFAULT_PROFILE_PICTURE_PATH = "static/css/images/Default-Profile-Picture.png";

    public UserDTO toDTO(User user) {
        UserDTO result = new UserDTO();
        result.setUserId(user.getUserId());
        result.setName(user.getName());
        result.setEmail(user.getEmail());

        if (user.getProfilePicture() == null) {
            result.setCustomProfilePicture(false);
            setProfilePicture(result);
        } else {
            result.setCustomProfilePicture(true);
            result.setProfilePicture(user.getProfilePicture());
        }

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
        result.setProfilePicture(userDTO.getProfilePicture());

        return result;
    }

    private void setProfilePicture(UserDTO result) {
        try {
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream(DEFAULT_PROFILE_PICTURE_PATH);

            if (inputStream == null) {
                fail("Unable to find resource");
            } else {
                result.setProfilePicture(IOUtils.toByteArray(inputStream));
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    public UserDTO toDTONoProfilePicture(User user) {
        UserDTO result = new UserDTO();
        result.setUserId(user.getUserId());
        result.setName(user.getName());
        result.setEmail(user.getEmail());
        result.setProfilePicture(null);

        return result;
    }

    public List<UserDTO> toDTONoProfilePicture(List<User> userList) {
        return userList.stream().map(this::toDTONoProfilePicture).collect(Collectors.toList());
    }
}
