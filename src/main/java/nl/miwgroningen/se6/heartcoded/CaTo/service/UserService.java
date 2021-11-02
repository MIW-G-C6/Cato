package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserWithPasswordDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.UserDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Gets data from the user repository and gives it to the controllers.
 */

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOConverter userDTOConverter;

    public UserService(UserRepository userRepository, UserDTOConverter userDTOConverter) {
        this.userRepository = userRepository;
        this.userDTOConverter = userDTOConverter;
    }

    public List<UserDTO> findAllUsers() {
        List<User> findUsers = userRepository.findAll();

        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : findUsers) {
            userDTOList.add(userDTOConverter.toDTO(user));
        }

        List<UserDTO> allUsers = new ArrayList<>();

        for (UserDTO user : userDTOList) {
            allUsers.add(new UserDTO(user.getUserId(), user.getName(), user.getEmail()));
        }
        return allUsers;
    }

    public UserDTO getById(Integer userId) {
        UserDTO user = new UserDTO();
        UserDTO userTemp = userDTOConverter.toDTO(userRepository.getById(userId));
        user.setUserId(userTemp.getUserId());
        user.setName(userTemp.getName());
        user.setEmail(userTemp.getEmail());

        return user;
    }

    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    public void saveUser(UserDTO user) {
        userRepository.save(userDTOConverter.toModel(user));
    }

    public void saveNewUser(UserWithPasswordDTO user) {
        userRepository.save( userDTOConverter.toModelWithPassword(user) );
    }

    public Optional<UserDTO> findUserByEmail(String email) {
        Optional<UserDTO> userDTO = Optional.empty();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            userDTO = Optional.of(userDTOConverter.toDTO(user.get()));
        }

        return userDTO;
    }

    public boolean emailInUse(String email) {
        return findUserByEmail(email).isPresent();
    }
}
