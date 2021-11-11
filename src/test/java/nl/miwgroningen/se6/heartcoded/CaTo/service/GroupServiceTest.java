package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.GroupMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupServiceTest {

    private MemberRepository memberRepository;
    private GroupRepository groupRepository;

    private GroupMapper groupMapper;
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        memberRepository = Mockito.mock(MemberRepository.class);
        groupRepository = Mockito.mock(GroupRepository.class);
        groupMapper = new GroupMapper();

        groupService = new GroupService(memberRepository, groupRepository, groupMapper);
    }

    @Test
    void findAllGroupsTest() {
        List<Group> groupList = new ArrayList<>();
        groupList.add(new Group(1, "testGroup1", new ArrayList<>()));
        groupList.add(new Group(2, "testGroup2", new ArrayList<>()));

        when(groupRepository.findAll()).thenReturn(groupList);

        List<GroupDTO> result = groupService.findAllGroups();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getGroupId());
        assertEquals("testGroup1", result.get(0).getGroupName());

        assertEquals(2, result.get(1).getGroupId());
        assertEquals("testGroup2", result.get(1).getGroupName());
    }

    @Test
    void deleteGroupByIdTest() {
        Group testGroup = new Group(1, "testGroup", new ArrayList<>());
        groupService.deleteGroupById(testGroup.getGroupId());

        verify(groupRepository, times(1)).deleteById(testGroup.getGroupId());
    }

    @Test
    void saveGroupTest() {
        List<Member> memberList = new ArrayList<>();

        Group testGroup = new Group(1, "testGroup", new ArrayList<>());

        memberList.add(new Member(testGroup, new User(), "Caregiver", true));
        memberList.add(new Member(testGroup, new User(), "Client", false));
        memberList.add(new Member(testGroup, new User(), "Caregiver", false));
        memberList.add(new Member(testGroup, new User(), "Client", true));

        testGroup.setMemberList(memberList);

        when(memberRepository.getAllByGroupGroupId(1)).thenReturn(memberList);
        when(groupRepository.save(testGroup)).thenReturn(testGroup);

        groupService.saveGroup(groupMapper.toDTO(testGroup));

        Mockito.verify(groupRepository, times(1)).save(any(Group.class));
    }

    @Test
    void getGroupByIdTest() {
        Group testGroup = new Group(1, "testGroup", new ArrayList<>());

        when(groupRepository.getById(1)).thenReturn(testGroup);

        GroupDTO result = groupService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getGroupId());
        assertEquals("testGroup", result.getGroupName());
    }
}