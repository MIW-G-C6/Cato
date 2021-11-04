package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GroupMapperTest {

    private GroupMapper groupMapper;

    @BeforeEach
    void setUp() {
        groupMapper = new GroupMapper();
    }

    @Test
    void groupToDTOTest() {
        Group testGroup = new Group();
        testGroup.setGroupId(1);
        testGroup.setGroupName("TESTGROUP");
        testGroup.setMemberList(new ArrayList<>());

        GroupDTO groupDTO = groupMapper.toDTO(testGroup);
        assertEquals("TESTGROUP", groupDTO.getGroupName());
        assertEquals(1, groupDTO.getGroupId());

    }

    @Test
    void groupDTOToGroupTest() {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupId(1);
        groupDTO.setGroupName("TESTGROUP");

        Group group = groupMapper.toGroup(groupDTO);
        Assertions.assertEquals("TESTGROUP", group.getGroupName());
        Assertions.assertEquals(1, group.getGroupId());
    }
}