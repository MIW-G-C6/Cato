package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserEditPasswordDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.UserEditPasswordMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserEditPasswordMapperTest {

    private UserEditPasswordMapper userEditPasswordMapper;

    private User user1;

    @BeforeEach
    void setUp() {
        userEditPasswordMapper = new UserEditPasswordMapper();

        user1 = new User();
        user1.setUserId(1);
        user1.setName("First");
        user1.setEmail("test@test.com");
        user1.setPassword("Password");
    }

    @Test
    void toDTO() {
        UserEditPasswordDTO userEditPasswordDTO = userEditPasswordMapper.toDTO(user1);

        assertEquals(1, userEditPasswordDTO.getUserId());
        assertEquals("First", userEditPasswordDTO.getName());
        assertEquals("test@test.com", userEditPasswordDTO.getEmail());
    }

    @Test
    void optionalEmptyToDTO() {
        Optional<User> optionalUser = Optional.empty();

        assertTrue(userEditPasswordMapper.toDTO(optionalUser).isEmpty());
    }

    @Test
    void optionalPresentToDTO() {
        Optional<User> optionalUser = Optional.of(user1);

        assertTrue(userEditPasswordMapper.toDTO(optionalUser).isPresent());
    }

    @Test
    void toUser() {
        UserEditPasswordDTO userEditPasswordDTO = new UserEditPasswordDTO();
        userEditPasswordDTO.setUserId(1);
        userEditPasswordDTO.setName("First");
        userEditPasswordDTO.setEmail("test@test.com");
        userEditPasswordDTO.setOldPassword("OldPassword");
        userEditPasswordDTO.setNewPassword("NewPassword");
        userEditPasswordDTO.setConfirmNewPassword("NewPassword");

        User user = userEditPasswordMapper.toUser(userEditPasswordDTO);

        assertEquals(1, user.getUserId());
        assertEquals("First", user.getName());
        assertEquals("test@test.com", user.getEmail());
        assertNull(user.getPassword());
    }
}