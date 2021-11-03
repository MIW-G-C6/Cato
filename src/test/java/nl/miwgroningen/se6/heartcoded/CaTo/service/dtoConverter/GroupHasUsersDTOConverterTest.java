package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Tests the GroupHasUsersDTOConverter methods
 */


class GroupHasUsersDTOConverterTest {

    private  GroupHasUsersDTOConverter groupHasUsersDTOConverter;

    protected GroupHasUsersDTOConverterTest(GroupHasUsersDTOConverter groupHasUsersDTOConverter) {
        this.groupHasUsersDTOConverter = groupHasUsersDTOConverter;
    }

    @Test
    void toDTOTest() {
        User user = new User(100, "Test User", "testemail@example.com");
        Group group = new Group();
        group.setGroupId(123);
        group.setGroupName("Test group");

        GroupHasUsersDTO groupHasUsersDTO = groupHasUsersDTOConverter.toDTO(
                new GroupHasUsers(group, user, "Caregiver", true));

        assertEquals(100, groupHasUsersDTO.getUser().getUserId());
        assertEquals("Test group", groupHasUsersDTO.getGroup().getGroupName());
        assertEquals("Caregiver", groupHasUsersDTO.getUserRole());
        assertTrue(groupHasUsersDTO.isAdmin());
    }

    @Test
    void toModelTest() {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupId(123);
        groupDTO.setGroupName("Test group");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(100);
        userDTO.setName("Test User");
        userDTO.setEmail("testemail@example.com");

        GroupHasUsers groupHasUsersModel = groupHasUsersDTOConverter.toModel(
                new GroupHasUsersDTO(groupDTO, userDTO, "Caregiver", true));


        assertEquals("Test group", groupHasUsersModel.getGroup().getGroupName());
        assertEquals("testemail@example.com", groupHasUsersModel.getUser().getEmail());
        assertEquals("Caregiver", groupHasUsersModel.getUserRole());
        assertTrue(groupHasUsersModel.isAdmin());
    }
}
