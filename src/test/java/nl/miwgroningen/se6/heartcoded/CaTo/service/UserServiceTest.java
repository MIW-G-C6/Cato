package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserEditPasswordDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private GroupRepository groupRepository;
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private UserMapper userMapper;
    private GroupMapper groupMapper;
    private UserLoginMapper userLoginMapper;
    private UserRegistrationMapper userRegistrationMapper;
    private UserEditPasswordMapper userEditPasswordMapper;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        groupRepository = Mockito.mock(GroupRepository.class);

        passwordEncoder = new BCryptPasswordEncoder();
        userMapper = new UserMapper();
        groupMapper = new GroupMapper();
        userLoginMapper = new UserLoginMapper();
        userRegistrationMapper = new UserRegistrationMapper();
        userEditPasswordMapper = new UserEditPasswordMapper();

        userService = new UserService(groupRepository, userRepository, userMapper, groupMapper,
                userLoginMapper, userRegistrationMapper, userEditPasswordMapper, passwordEncoder);
    }

    @Test
    void getUserEditDTOByIdTest() {
        User user = new User(1, "First", "test@test.com");

        when(userRepository.getById(1)).thenReturn(user);

        UserEditPasswordDTO result = userService.getUserEditDTOById(1);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals("First", result.getName());
        assertEquals("test@test.com", result.getEmail());
    }

    @Test
    void editUserTest() {
        UserEditPasswordDTO userEditPasswordDTO = new UserEditPasswordDTO();
        userEditPasswordDTO.setUserId(1);
        userEditPasswordDTO.setName("First");
        userEditPasswordDTO.setEmail("test@test.com");
        userEditPasswordDTO.setNewPassword("NewPassword");

        User user = new User(1, "First", "test@test.com");

        when(userRepository.getById(user.getUserId())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.editPassword(userEditPasswordDTO);

        Mockito.verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void passwordMatchesTest() {
        User user = new User();
        String encodedPW = passwordEncoder.encode("Password");
        user.setPassword(encodedPW);

        Integer userId = 1;
        String oldPassword = "Password";

        when(userRepository.getById(userId)).thenReturn(user);

        assertTrue(userService.passwordMatches(oldPassword, userId));
    }
}