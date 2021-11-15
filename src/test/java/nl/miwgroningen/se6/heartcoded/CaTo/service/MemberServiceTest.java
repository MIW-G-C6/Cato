package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.CircleRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.CircleMapper;
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
        CircleMapper circleMapper = new CircleMapper();
        MemberSiteAdminMapper memberSiteAdminMapper = new MemberSiteAdminMapper();

        CircleRepository circleRepository = Mockito.mock(CircleRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        memberRepository = Mockito.mock(MemberRepository.class);

        memberService = new MemberService(memberMapper, circleMapper, memberSiteAdminMapper,
                circleRepository, userRepository, memberRepository);
    }

    @Test
    void findAllCaregiversInACircleTest() {
        List<Member> memberList = new ArrayList<>();

        Circle testCircle = new Circle(1, "testCircle1", new ArrayList<>());
        User testUser1 = new User();
        User testUser2 = new User();

        testUser1.setUserId(1);
        testUser2.setUserId(2);

        memberList.add(new Member(testCircle, testUser1, "Caregiver", true));
        memberList.add(new Member(testCircle, new User(), "Client", false));
        memberList.add(new Member(testCircle, testUser2, "Caregiver", false));
        memberList.add(new Member(testCircle, new User(), "Client", true));

        when(memberRepository.getAllByCircleCircleId(1)).thenReturn(memberList);

        List<MemberDTO> result = memberService.findAllCaregiversByCircleId(1);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getCircleId());
        assertEquals(1, result.get(1).getCircleId());

        assertEquals(1, result.get(0).getUserId());
        assertEquals(2, result.get(1).getUserId());

        assertEquals("Caregiver", result.get(0).getRole());
        assertEquals("Caregiver", result.get(1).getRole());
    }

    @Test
    void findAllClientsInACircleTest() {

        List<Member> memberList = new ArrayList<>();

        Circle testCircle = new Circle(1, "testCircle1", new ArrayList<>());
        User testUser1 = new User();
        User testUser2 = new User();

        testUser1.setUserId(1);
        testUser2.setUserId(2);

        memberList.add(new Member(testCircle, new User() , "Caregiver", true));
        memberList.add(new Member(testCircle, testUser1, "Client", false));
        memberList.add(new Member(testCircle, new User(), "Caregiver", false));
        memberList.add(new Member(testCircle, testUser2, "Client", true));

        when(memberRepository.getAllByCircleCircleId(1)).thenReturn(memberList);

        List<MemberDTO> result = memberService.findAllClientsInCircle(1);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getCircleId());
        assertEquals(1, result.get(1).getCircleId());

        assertEquals(1, result.get(0).getUserId());
        assertEquals(2, result.get(1).getUserId());

        assertEquals("Client", result.get(0).getRole());
        assertEquals("Client", result.get(1).getRole());
    }
}