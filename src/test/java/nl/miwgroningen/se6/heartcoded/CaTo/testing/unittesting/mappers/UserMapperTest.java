package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.UserMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    
    private UserMapper userMapper;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();

        user1 = new User(100, "Test100", "100@test.com");
        user2 = new User(298, "Test298", "298@test.com");
        user3 = new User(4, "Test4", "4@test.com");
    }

    @Test
    @DisplayName("UserMapper ID")
    void userIDtoDTO() {
        UserDTO userDTO1 = userMapper.toDTO(user1);
        
        assertEquals(100, userDTO1.getUserId());
    }

    @Test
    @DisplayName("UserMapper Name")
    void userNametoDTO() {
        UserDTO userDTO1 = userMapper.toDTO(user1);

        assertEquals("Test100", userDTO1.getName());
    }

    @Test
    @DisplayName("UserMapper Email")
    void userEmailtoDTO() {
        UserDTO userDTO1 = userMapper.toDTO(user1);

        assertEquals("100@test.com", userDTO1.getEmail());
    }

    @Test
    @DisplayName("StreamMapping works")
    void listMappingToDTO() {
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        List<UserDTO> userDTOList = userMapper.toDTO(userList);

        assertEquals(100, userDTOList.get(0).getUserId());
        assertEquals(298, userDTOList.get(1).getUserId());
    }

    @Test
    @DisplayName("List size should be the same")
    void listSizeToDTO() {
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        List<UserDTO> userDTOList = userMapper.toDTO(userList);

        assertEquals(3, userDTOList.size());
    }

    @Test
    @DisplayName("Optional should be the same")
    void OptionalToDTO1() {
        Optional<User> user = Optional.of(user1);
        Optional<UserDTO> userDTO = userMapper.toDTO(user);

        assertEquals("100@test.com", userDTO.get().getEmail());
    }

    @Test
    void toUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(100);
        userDTO.setName("Test100");
        userDTO.setEmail("100@test.com");

        User user = userMapper.toUser(userDTO);

        assertEquals(100, user.getUserId());
        assertEquals("Test100", user.getName());
        assertEquals("100@test.com", user.getEmail());
    }
}