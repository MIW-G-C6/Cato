package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.CircleDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserEditPasswordDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserRegistrationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.CircleRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Gets data from the user repository and gives it to the controllers.
 */

@Service
public class UserService {

    private final CircleRepository circleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CircleMapper circleMapper;
    private final UserLoginMapper userLoginMapper;
    private final UserRegistrationMapper userRegistrationMapper;
    private final UserEditPasswordMapper userEditPasswordMapper;
    private PasswordEncoder passwordEncoder;

    public UserService(CircleRepository circleRepository, UserRepository userRepository, UserMapper userMapper,
                       CircleMapper circleMapper, UserLoginMapper userLoginMapper,
                       UserRegistrationMapper userRegistrationMapper, UserEditPasswordMapper userEditPasswordMapper,
                       PasswordEncoder passwordEncoder) {
        this.circleRepository = circleRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.circleMapper = circleMapper;
        this.userLoginMapper = userLoginMapper;
        this.userRegistrationMapper = userRegistrationMapper;
        this.userEditPasswordMapper = userEditPasswordMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAllUsers() {
        return userMapper.toDTO(userRepository.findAll());
    }

    public List<UserDTO> findAllRegisteredUsers() {
        List<User> allUsers = userRepository.findAll();
        removeSiteAdminFromList(allUsers);
        return userMapper.toDTO(allUsers);
    }

    public UserDTO getById(Integer userId) {
        return userMapper.toDTO(userRepository.getById(userId));
    }

    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    public UserEditPasswordDTO getUserEditDTOById(Integer userId) {
        return userEditPasswordMapper.toDTO(userRepository.getById(userId));
    }

    public void editUserInfo(UserDTO user) {
        User result = userRepository.getById(user.getUserId());

        result.setName(user.getName());
        result.setEmail(user.getEmail());

        userRepository.save(result);
    }

    public void editPassword(UserEditPasswordDTO user) {
        User result = userRepository.getById(user.getUserId());

        result.setPassword(passwordEncoder.encode(user.getNewPassword()));

        userRepository.save(result);

        //TODO maybe this needs an exception throw??
    }

    public void saveNewUser(UserRegistrationDTO userDTO) {
        if(userDTO.getPassword().equals(userDTO.getPasswordCheck())) {

            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            User user = userRegistrationMapper.toUser(userDTO);
            user.setUserRole("ROLE_USER");

            try {
                InputStream inputStream = getClass()
                        .getClassLoader()
                        .getResourceAsStream("static/css/images/Default-Profile-Picture.png");

                if (inputStream == null) {
                    fail("Unable to find resource");
                } else {
                    user.setProfilePicture(IOUtils.toByteArray(inputStream));
                }
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }

            userRepository.save(user);
        }
        //TODO maybe this needs an exception throw??
    }

    public void saveAdmin() {
        User adminUser = new User();
        adminUser.setUserRole("ROLE_ADMIN");
        adminUser.setName("Admin");
        adminUser.setEmail("Admin@admin.com");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(adminUser);
    }

    public Optional<UserRegistrationDTO> findUserByEmail(String email) {
        return userRegistrationMapper.toDTO(userRepository.findByEmail(email));
    }

    public Optional<Integer> findUserIdByEmail(String email) {
        Optional<Integer> userId = Optional.empty();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            userId = Optional.of(user.get().getUserId());
        }

        return userId;
    }

    public boolean emailInUse(String email) {
        return findUserByEmail(email).isPresent();
    }

    public UserDTO getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getById(user.getUserId());
    }

    public List<CircleDTO> getLastThreeCirclesByUserId(Integer userId) {
        List<CircleDTO> result = new ArrayList<>();
        List<Integer> circleList = getLastThreeCircleIdByUserId(userId);
        for (Integer circleId : circleList) {
            if(!(circleId == 0)) {
                result.add(circleMapper.toDTO(circleRepository.getById(circleId)));
            } else {
                CircleDTO circleZero = new CircleDTO();
                circleZero.setCircleId(0);
                circleZero.setCircleName("Non-existing circle");
                result.add(circleZero);
            }
        }
        return result;
    }

    public void checkForCircleDeletion() {
        User user = userRepository.getById(getCurrentUser().getUserId());
        List<Integer> circleList = getLastThreeCircleIdByUserId(user.getUserId());
        for (Integer circleId : circleList) {
            Optional<Circle> circle = circleRepository.findById(circleId);
            if (circle.isEmpty()) {
                ifCircleIsDeletedSetCircles(user, circleId);
            }
        }
        userRepository.save(user);
    }

    public void ifCircleIsDeletedSetCircles(User user, Integer circleId) {
        if(user.getCircleOne() == circleId) {
            user.setCircleOne(user.getCircleTwo());
            user.setCircleTwo(user.getCircleThree());
            user.setCircleThree(0);
        } else if(user.getCircleTwo() == circleId) {
            user.setCircleTwo(user.getCircleThree());
            user.setCircleThree(0);
        } else if(user.getCircleThree() == circleId) {
            user.setCircleThree(0);
        }
    }

    public void addCircleToLastThreeCircles(Integer circleId) {
        User user = userRepository.getById(getCurrentUser().getUserId());
        List<Integer> lastThreeCircleList = getLastThreeCircleIdByUserId(user.getUserId());
        if(lastThreeCircleList.contains(circleId)) {
            if (lastThreeCircleList.get(1).equals(circleId)) {
                user.setCircleTwo(lastThreeCircleList.get(0));
                user.setCircleOne(circleId);
            } else if (lastThreeCircleList.get(2).equals(circleId)) {
                user.setCircleThree(lastThreeCircleList.get(1));
                user.setCircleTwo(lastThreeCircleList.get(0));
                user.setCircleOne(circleId);
            }
        } else {
            user.setAllThreeCircles(lastThreeCircleList, circleId);
        }
        userRepository.save(user);
    }


    private List<Integer> getLastThreeCircleIdByUserId(Integer userId) {
        User user = userRepository.getById(userId);
        List<Integer> result = new ArrayList<>();
        result.add(0, user.getCircleOne());
        result.add(1, user.getCircleTwo());
        result.add(2, user.getCircleThree());
        return result;
    }

    public boolean currentUserIsSiteAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean userIsSiteAdmin(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }

    public Integer getCircleOne(Integer userId) {
       return userRepository.getById(userId).getCircleOne();
    }

    public Integer getCircleTwo(Integer userId) {
        return userRepository.getById(userId).getCircleTwo();
    }

    public Integer getCircleThree(Integer userId) {
        return userRepository.getById(userId).getCircleThree();
    }

    public List<UserDTO> findWithNameContains(String keyword) {
        List<User> userList = userRepository.findByNameContains(keyword);
        removeSiteAdminFromList(userList);
        return userMapper.toDTO(userList);
    }
    private void removeSiteAdminFromList(List<User> userList) {
        userList.removeIf(user -> user.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")));
    }
    public boolean passwordMatches(String oldPassword, Integer userId) {
        return passwordEncoder.matches(oldPassword, userRepository.getById(userId).getPassword());
    }
}
