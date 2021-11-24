package nl.miwgroningen.se6.heartcoded.CaTo.testing.integrationtesting;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserRegistrationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Tests the integration of the user with the database.
 */

@SpringBootTest
public class UserIntegrationTest {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public UserIntegrationTest(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Test
    void registerAndDeleteUserTest() {
        UserRegistrationDTO testUser = new UserRegistrationDTO("TEST", "TEST_PW",
                "TEST_PW", "TEST@EXAMPLE.COM");

        userService.saveNewUser(testUser);
        Optional<User> found = userRepository.findByEmail("TEST@EXAMPLE.COM");

        assertFalse(found.isEmpty());
        assertEquals("TEST", found.get().getName());
        assertEquals("TEST@EXAMPLE.COM", found.get().getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertTrue(passwordEncoder.matches("TEST_PW", found.get().getPassword()));

        userService.deleteUserById(found.get().getUserId());
        Optional<User> result = userRepository.findByEmail("TEST@EXAMPLE.COM");

        assertTrue(result.isEmpty());
    }
}
