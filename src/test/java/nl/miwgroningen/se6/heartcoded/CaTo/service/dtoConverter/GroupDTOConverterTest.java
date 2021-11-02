package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupDTOConverterTest {

    private GroupDTOConverter groupDTOConverter;

    public GroupDTOConverterTest(GroupDTOConverter groupDTOConverter) {
        this.groupDTOConverter = groupDTOConverter;
    }

    @Test
    void toDTO() {
        Group group = new Group();
        group.setGroupName("TestGroep");
        GroupDTO groupDTO = groupDTOConverter.toDTO(group);
        assertEquals("TestGroep", groupDTO.getGroupName());
    }

    @Test
    void toModel() {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupName("TestGroep");
        Group group = groupDTOConverter.toModel(groupDTO);
        assertEquals("TestGroep", group.getGroupName());
    }
}