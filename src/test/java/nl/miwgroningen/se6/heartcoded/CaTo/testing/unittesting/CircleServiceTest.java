package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.CircleDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.MemberMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.CircleRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.CircleService;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.CircleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CircleServiceTest {

    private MemberRepository memberRepository;
    private CircleRepository circleRepository;
    private UserRepository userRepository;

    private CircleMapper circleMapper;
    private MemberMapper memberMapper;
    
    private CircleService circleService;

    @BeforeEach
    void setUp() {
        memberRepository = Mockito.mock(MemberRepository.class);
        circleRepository = Mockito.mock(CircleRepository.class);
        userRepository = Mockito.mock(UserRepository.class);

        circleMapper = new CircleMapper();
        memberMapper = new MemberMapper();

        circleService = new CircleService(memberRepository, circleRepository, userRepository,
                circleMapper, memberMapper);
    }

    @Test
    void findAllCirclesTest() {
        List<Circle> circleList = new ArrayList<>();
        circleList.add(new Circle(1, "testCircle1", new ArrayList<>()));
        circleList.add(new Circle(2, "testCircle2", new ArrayList<>()));

        when(circleRepository.findAll()).thenReturn(circleList);

        List<CircleDTO> result = circleService.findAllCircles();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getCircleId());
        assertEquals("testCircle1", result.get(0).getCircleName());

        assertEquals(2, result.get(1).getCircleId());
        assertEquals("testCircle2", result.get(1).getCircleName());
    }

    @Test
    void deleteCircleByIdTest() {
        Circle testCircle = new Circle(1, "testCircle", new ArrayList<>());
        circleService.deleteCircleById(testCircle.getCircleId());

        verify(circleRepository, times(1)).deleteById(testCircle.getCircleId());
    }

    @Test
    void saveCircleTest() {
        List<Member> memberList = new ArrayList<>();

        Circle testCircle = new Circle(1, "testCircle", new ArrayList<>());

        memberList.add(new Member(testCircle, new User(), "Caregiver", true));
        memberList.add(new Member(testCircle, new User(), "Client", false));
        memberList.add(new Member(testCircle, new User(), "Caregiver", false));
        memberList.add(new Member(testCircle, new User(), "Client", true));

        testCircle.setMemberList(memberList);

        when(memberRepository.getAllByCircleCircleId(1)).thenReturn(memberList);
        when(circleRepository.save(testCircle)).thenReturn(testCircle);

        circleService.saveCircle(circleMapper.toDTO(testCircle));

        Mockito.verify(circleRepository, times(1)).save(any(Circle.class));
    }

    @Test
    void getCircleByIdTest() {
        Circle testCircle = new Circle(1, "testCircle", new ArrayList<>());

        when(circleRepository.getById(1)).thenReturn(testCircle);

        CircleDTO result = circleService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getCircleId());
        assertEquals("testCircle", result.getCircleName());
    }
}