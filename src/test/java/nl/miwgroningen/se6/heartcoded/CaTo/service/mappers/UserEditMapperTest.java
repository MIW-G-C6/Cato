package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserEditDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserEditMapperTest {

    private UserEditMapper userEditMapper;

    private User user1;

    @BeforeEach
    void setUp() {
        userEditMapper = new UserEditMapper();
        user1 = new User();
        user1.setUserId(1);
        user1.setName("First");
        user1.setEmail("test@test.com");
        user1.setPassword("Password");
    }

    @Test
    void toDTO() {
        UserEditDTO userEditDTO = userEditMapper.toDTO(user1);

        assertEquals(1, userEditDTO.getUserId());
        assertEquals("First", userEditDTO.getName());
        assertEquals("test@test.com", userEditDTO.getEmail());
    }

    @Test
    void optionalEmptyToDTO() {
        Optional<User> optionalUser = Optional.empty();

        assertTrue(userEditMapper.toDTO(optionalUser).isEmpty());
    }

    @Test
    void optionalPresentToDTO() {
        Optional<User> optionalUser = Optional.of(user1);

        assertTrue(userEditMapper.toDTO(optionalUser).isPresent());
    }

    @Test
    void toUser() {
        UserEditDTO userEditDTO = new UserEditDTO();
        userEditDTO.setUserId(1);
        userEditDTO.setName("First");
        userEditDTO.setEmail("test@test.com");
        userEditDTO.setOldPassword("OldPassword");
        userEditDTO.setNewPassword("NewPassword");
        userEditDTO.setConfirmNewPassword("NewPassword");

        User user = userEditMapper.toUser(userEditDTO);

        assertEquals(1, user.getUserId());
        assertEquals("First", user.getName());
        assertEquals("test@test.com", user.getEmail());
        assertNull(user.getPassword());
    }
}