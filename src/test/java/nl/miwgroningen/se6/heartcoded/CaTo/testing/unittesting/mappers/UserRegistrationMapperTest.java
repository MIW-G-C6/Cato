package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserRegistrationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.UserRegistrationMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationMapperTest {

    private UserRegistrationMapper userRegistrationMapper;

    private User user1;


    @BeforeEach
    void setUp() {
        userRegistrationMapper = new UserRegistrationMapper();

        user1 = new User(100, "Test100", "100@test.com");
        user1.setPassword("PW123");
    }

    @Test
    void toDTO() {
        UserRegistrationDTO userRegistrationDTO = userRegistrationMapper.toDTO(user1);

        assertEquals(100, userRegistrationDTO.getUserId());
        assertEquals("Test100", userRegistrationDTO.getName());
        assertEquals("100@test.com", userRegistrationDTO.getEmail());
        assertEquals("PW123", userRegistrationDTO.getPassword());
    }

    @Test
    void OptionalToDTO() {
        Optional<User> user = Optional.of(user1);
        Optional<UserRegistrationDTO> registrationDTO = userRegistrationMapper.toDTO(user);

        assertEquals(100, registrationDTO.get().getUserId());
        assertEquals("Test100", registrationDTO.get().getName());
        assertEquals("100@test.com", registrationDTO.get().getEmail());
        assertEquals("PW123", registrationDTO.get().getPassword());
    }

    @Test
    void toUser() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUserId(100);
        userRegistrationDTO.setName("Test100");
        userRegistrationDTO.setEmail("100@test.com");
        userRegistrationDTO.setPassword("PW123");
        userRegistrationDTO.setPasswordCheck("PW123");

        User user = userRegistrationMapper.toUser(userRegistrationDTO);

        assertEquals("Test100", user.getName());
        assertEquals("100@test.com", user.getEmail());
        assertEquals("PW123", user.getPassword());

    }
}