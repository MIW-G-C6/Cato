package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.GroupMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.MemberMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.MemberSiteAdminMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MemberServiceTest {

    private MemberRepository memberRepository;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MemberMapper memberMapper = new MemberMapper();
        GroupMapper groupMapper = new GroupMapper();
        MemberSiteAdminMapper memberSiteAdminMapper = new MemberSiteAdminMapper();

        GroupRepository groupRepository = Mockito.mock(GroupRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        memberRepository = Mockito.mock(MemberRepository.class);

        memberService = new MemberService(memberMapper, groupMapper, memberSiteAdminMapper,
                groupRepository, userRepository, memberRepository);
    }

    @Test
    void findAllCaregiversInAGroupTest() {
        List<Member> memberList = new ArrayList<>();

        Group testGroup = new Group(1, "testGroup1", new ArrayList<>());
        User testUser1 = new User();
        User testUser2 = new User();

        testUser1.setUserId(1);
        testUser2.setUserId(2);

        memberList.add(new Member(testGroup, testUser1, "Caregiver", true));
        memberList.add(new Member(testGroup, new User(), "Client", false));
        memberList.add(new Member(testGroup, testUser2, "Caregiver", false));
        memberList.add(new Member(testGroup, new User(), "Client", true));

        when(memberRepository.getAllByGroupGroupId(1)).thenReturn(memberList);

        List<MemberDTO> result = memberService.findAllCaregiversByGroupId(1);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getGroupId());
        assertEquals(1, result.get(1).getGroupId());

        assertEquals(1, result.get(0).getUserId());
        assertEquals(2, result.get(1).getUserId());

        assertEquals("Caregiver", result.get(0).getRole());
        assertEquals("Caregiver", result.get(1).getRole());
    }

    @Test
    void findAllClientsInAGroupTest() {
        List<Member> memberList = new ArrayList<>();

        Group testGroup = new Group(1, "testGroup1", new ArrayList<>());
        User testUser1 = new User();
        User testUser2 = new User();

        testUser1.setUserId(1);
        testUser2.setUserId(2);

        memberList.add(new Member(testGroup, new User() , "Caregiver", true));
        memberList.add(new Member(testGroup, testUser1, "Client", false));
        memberList.add(new Member(testGroup, new User(), "Caregiver", false));
        memberList.add(new Member(testGroup, testUser2, "Client", true));

        when(memberRepository.getAllByGroupGroupId(1)).thenReturn(memberList);

        List<MemberDTO> result = memberService.findAllClientsInGroup(1);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getGroupId());
        assertEquals(1, result.get(1).getGroupId());

        assertEquals(1, result.get(0).getUserId());
        assertEquals(2, result.get(1).getUserId());

        assertEquals("Client", result.get(0).getRole());
        assertEquals("Client", result.get(1).getRole());
    }
}