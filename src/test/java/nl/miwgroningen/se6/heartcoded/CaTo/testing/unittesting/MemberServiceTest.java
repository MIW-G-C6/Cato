package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.CircleRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.CircleMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.MemberMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.MemberSiteAdminMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.expression.spel.ast.BooleanLiteral;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MemberServiceTest {

    private MemberRepository memberRepository;

    private MemberService memberService;

    private Authentication authentication;

    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        MemberMapper memberMapper = new MemberMapper();
        CircleMapper circleMapper = new CircleMapper();
        MemberSiteAdminMapper memberSiteAdminMapper = new MemberSiteAdminMapper();

        CircleRepository circleRepository = Mockito.mock(CircleRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        memberRepository = Mockito.mock(MemberRepository.class);

        authentication = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);

        SecurityContextHolder.setContext(securityContext);


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

    @Test
    void findAllClients() {
        List<Member> memberList = new ArrayList<>();
        memberList.add(new Member(new Circle(), new User() , "Caregiver", true));
        memberList.add(new Member(new Circle(), new User(), "Client", false));
        memberList.add(new Member(new Circle(), new User(), "Caregiver", false));
        memberList.add(new Member(new Circle(), new User(), "Client", true));

        when(memberRepository.findAll()).thenReturn(memberList);

        List<MemberDTO> result = memberService.findAllClients();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertAll(() -> assertEquals("Client", result.get(0).getRole()),
                () -> assertEquals("Client", result.get(1).getRole()));
    }

    @Test
    void userIsMemberOfCircleTest() {
        User testUser = new User();
        testUser.setUserId(1);

        List<Member> memberList1 = new ArrayList<>();

        memberList1.add(new Member(new Circle(), new User() , "Caregiver", true));
        memberList1.add(new Member(new Circle(), new User(), "Client", false));
        memberList1.add(new Member(new Circle(), testUser, "Caregiver", false));

        List<Member> memberList2 = new ArrayList<>();

        memberList2.add(new Member(new Circle(), new User() , "Caregiver", true));
        memberList2.add(new Member(new Circle(), new User(), "Client", false));
        memberList2.add(new Member(new Circle(), new User(), "Caregiver", false));


        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(testUser);
        when(memberRepository.getAllByCircleCircleId(1)).thenReturn(memberList1);
        when(memberRepository.getAllByCircleCircleId(2)).thenReturn(memberList2);

        boolean result1 = memberService.userIsMemberOfCircle(1);
        boolean result2 = memberService.userIsMemberOfCircle(2);

        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    void userIsCircleAdminTest() {
        Circle testCircle1 = new Circle(1, "testCircle1", new ArrayList<>());
        Circle testCircle2 = new Circle(1, "testCircle1", new ArrayList<>());

        User testUser = new User();
        testUser.setUserId(1);

        Member testMember1 = new Member(testCircle1, testUser, "Caregiver", true);
        Member testMember2 = new Member(testCircle2, testUser, "Caregiver", false);

        when(memberRepository.findMemberByUserUserIdAndCircleCircleId(1, 1)).thenReturn(Optional.of(testMember1));
        when(memberRepository.findMemberByUserUserIdAndCircleCircleId(1, 2)).thenReturn(Optional.of(testMember2));

        assertTrue(memberService.userIsCircleAdmin(1, 1));
        assertFalse(memberService.userIsCircleAdmin(2, 1));
    }
}