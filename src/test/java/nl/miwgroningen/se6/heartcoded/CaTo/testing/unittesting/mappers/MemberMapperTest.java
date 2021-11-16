package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberMapperTest {

    private MemberMapper memberMapper;
    private User user1;
    private User user2;
    private Circle circle1;
    private Circle circle2;
    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        memberMapper = new MemberMapper();
        user1 = new User();
        user2 = new User();
        circle1 = new Circle();
        circle2 = new Circle();
        member1 = new Member();
        member2 = new Member();

        user1.setUserId(1);
        user1.setName("First");
        user2.setUserId(2);
        user2.setName("Second");

        circle1.setCircleId(3);
        circle1.setCircleName("Circle1");
        circle2.setCircleId(4);
        circle2.setCircleName("Circle2");

        member1.setUser(user1);
        member1.setCircle(circle1);
        member1.setUserRole("TestRole1");
        member1.setAdmin(true);

        member2.setUser(user2);
        member2.setCircle(circle2);
    }

    @Test
    void toDTO() {
        MemberDTO memberDTO = memberMapper.toDTO(member1);
        assertEquals(Member.getCircleRoleOptions(), memberDTO.getCircleRoleOptions());
        assertEquals(user1.getUserId(), memberDTO.getUserId());
        assertEquals(user1.getName(), memberDTO.getUserName());
        assertEquals(circle1.getCircleId(), memberDTO.getCircleId());
        assertEquals(member1.getUserRole(), memberDTO.getRole());
        assertEquals(member1.isAdmin(), memberDTO.isAdmin());
    }

    @Test
    void listToDTO() {
        List<Member> testList = new ArrayList<>();
        testList.add(member1);
        testList.add(member2);
        assertEquals(2, memberMapper.toDTO(testList).size());
    }

    @Test
    void optionalEmptyToDTO() {
        Optional<Member> optionalMember = Optional.empty();

        assertTrue(memberMapper.toDTO(optionalMember).isEmpty());
    }

    @Test
    void optionalPresentToDTO() {
        Optional<Member> optionalMember = Optional.of(member1);

        assertTrue(memberMapper.toDTO(optionalMember).isPresent());
    }

    @Test
    void toMember() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setRole("TestRole");
        memberDTO.setAdmin(true);
        Member member = memberMapper.toMember(memberDTO);
        assertEquals(memberDTO.getRole(), member.getUserRole());
        assertTrue(member.isAdmin());
    }
}