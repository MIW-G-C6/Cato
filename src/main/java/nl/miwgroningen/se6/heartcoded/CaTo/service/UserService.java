package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserRegistrationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.UserDTOConverter;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.UserLoginMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.UserMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.UserRegistrationMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Gets data from the user repository and gives it to the controllers.
 */

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserLoginMapper userLoginMapper;
    private final UserRegistrationMapper userRegistrationMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper,
                       UserLoginMapper userLoginMapper, UserRegistrationMapper userRegistrationMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userLoginMapper = userLoginMapper;
        this.userRegistrationMapper = userRegistrationMapper;
    }

    public List<UserDTO> findAllUsers() {
        return userMapper.toDTO(userRepository.findAll());
    }

    public UserDTO getById(Integer userId) {
        return userMapper.toDTO(userRepository.getById(userId));
    }

    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    public void saveUser(UserDTO user) {
        User result = userMapper.toUser(user);
        result.setMemberList(userRepository.getById(user.getUserId()).getMemberList());
        userRepository.save(result);
        //TODO maybe this needs an exception throw??
    }

    public void saveNewUser(UserRegistrationDTO user) {
        if(user.getPassword().equals(user.getPasswordCheck())) {
            userRepository.save(userRegistrationMapper.toUser(user));
        }
        //TODO maybe this needs an exception throw??
    }

    public Optional<UserDTO> findUserByEmail(String email) {
        return userMapper.toDTO(userRepository.findByEmail(email));
    }

    public boolean emailInUse(String email) {
        return findUserByEmail(email).isPresent();
    }
}
