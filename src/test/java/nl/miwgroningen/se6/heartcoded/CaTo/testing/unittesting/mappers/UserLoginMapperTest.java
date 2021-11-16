package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserLoginDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginMapperTest {

    @Test
    void toDTO() {
        UserLoginMapper userLoginMapper = new UserLoginMapper();

        User user = new User(100, "Test100", "100@test.com");
        user.setPassword("PW123");

        UserLoginDTO userLoginDTO = userLoginMapper.toDTO(user);

        assertEquals("100@test.com", userLoginDTO.getEmail());
        assertEquals("PW123", userLoginDTO.getPassword());
    }
}