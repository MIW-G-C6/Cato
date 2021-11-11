package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserEditDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserRegistrationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final GroupMapper groupMapper;
    private final UserLoginMapper userLoginMapper;
    private final UserRegistrationMapper userRegistrationMapper;
    private final UserEditMapper userEditMapper;
    private PasswordEncoder passwordEncoder;

    public UserService(GroupRepository groupRepository, UserRepository userRepository, UserMapper userMapper,
                       GroupMapper groupMapper, UserLoginMapper userLoginMapper,
                       UserRegistrationMapper userRegistrationMapper, UserEditMapper userEditMapper,
                       PasswordEncoder passwordEncoder) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.groupMapper = groupMapper;
        this.userLoginMapper = userLoginMapper;
        this.userRegistrationMapper = userRegistrationMapper;
        this.userEditMapper = userEditMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAllUsers() {
        return userMapper.toDTO(userRepository.findAll());
    }

    public List<UserDTO> findAllRegisteredUsers() {
        List<User> allUsers = userRepository.findAll();
        allUsers.removeIf(user -> user.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")));
        return userMapper.toDTO(allUsers);
    }

    public UserDTO getById(Integer userId) {
        return userMapper.toDTO(userRepository.getById(userId));
    }

    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    public UserEditDTO getUserEditDTOById(Integer userId) {
        return userEditMapper.toDTO(userRepository.getById(userId));
    }

    public void editUser(UserEditDTO user) {
        User result = userEditMapper.toUser(user);
        result.setMemberList(userRepository.getById(user.getUserId()).getMemberList());
        result.setUserRole("ROLE_USER");

        result.setPassword(passwordEncoder.encode(user.getNewPassword()));

        userRepository.save(result);

        //TODO maybe this needs an exception throw??
    }

    public void saveNewUser(UserRegistrationDTO userDTO) {
        if(userDTO.getPassword().equals(userDTO.getPasswordCheck())) {

            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            User user = userRegistrationMapper.toUser(userDTO);
            user.setUserRole("ROLE_USER");
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

    public List<GroupDTO> getLastThreeGroupsByUserId(Integer userId) {
        List<GroupDTO> result = new ArrayList<>();
        List<Integer> groupList = getLastThreeGroupIdByUserId(userId);
        for (Integer groupId : groupList) {
            if(!(groupId == 0)) {
                result.add(groupMapper.toDTO(groupRepository.getById(groupId)));
            } else {
                GroupDTO groupZero = new GroupDTO();
                groupZero.setGroupId(0);
                groupZero.setGroupName("Non-existing group");
                result.add(groupZero);
            }
        }
        return result;
    }

    public void checkForGroupDeletion() {
        User user = userRepository.getById(getCurrentUser().getUserId());
        List<Integer> groupList = getLastThreeGroupIdByUserId(user.getUserId());
        for (Integer groupId : groupList) {
            Optional<Group> group = groupRepository.findById(groupId);
            if (group.isEmpty()) {
                ifGroupIsDeletedSetGroups(user, groupId);
            }
        }
        userRepository.save(user);
    }

    public void ifGroupIsDeletedSetGroups(User user,Integer groupId) {
        if(user.getGroupOne() == groupId) {
            user.setGroupOne(user.getGroupTwo());
            user.setGroupTwo(user.getGroupThree());
            user.setGroupThree(0);
        } else if(user.getGroupTwo() == groupId) {
            user.setGroupTwo(user.getGroupThree());
            user.setGroupThree(0);
        } else if(user.getGroupThree() == groupId) {
            user.setGroupThree(0);
        }
    }

    public void addGroupToLastThreeGroups(Integer groupId) {
        User user = userRepository.getById(getCurrentUser().getUserId());
        List<Integer> lastThreeGroupList = getLastThreeGroupIdByUserId(user.getUserId());
        if(lastThreeGroupList.contains(groupId)) {
            if (lastThreeGroupList.get(1).equals(groupId)) {
                user.setGroupTwo(lastThreeGroupList.get(0));
                user.setGroupOne(groupId);
            } else if (lastThreeGroupList.get(2).equals(groupId)) {
                user.setGroupThree(lastThreeGroupList.get(1));
                user.setGroupTwo(lastThreeGroupList.get(0));
                user.setGroupOne(groupId);
            }
        } else {
            user.setAllThreeGroups(lastThreeGroupList, groupId);
        }
        userRepository.save(user);
    }


    private List<Integer> getLastThreeGroupIdByUserId(Integer userId) {
        User user = userRepository.getById(userId);
        List<Integer> result = new ArrayList<>();
        result.add(0, user.getGroupOne());
        result.add(1, user.getGroupTwo());
        result.add(2, user.getGroupThree());
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

    public Integer getGroupOne(Integer userId) {
       return userRepository.getById(userId).getGroupOne();
    }

    public Integer getGroupTwo(Integer userId) {
        return userRepository.getById(userId).getGroupTwo();
    }

    public Integer getGroupThree(Integer userId) {
        return userRepository.getById(userId).getGroupThree();
    }

    public boolean passwordMatches(String oldPassword, Integer userId) {
        return passwordEncoder.matches(oldPassword, userRepository.getById(userId).getPassword());
    }
}
