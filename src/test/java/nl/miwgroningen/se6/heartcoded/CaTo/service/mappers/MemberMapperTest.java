package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
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
    private Group group1;
    private Group group2;
    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        memberMapper = new MemberMapper();
        user1 = new User();
        user2 = new User();
        group1 = new Group();
        group2 = new Group();
        member1 = new Member();
        member2 = new Member();

        user1.setUserId(1);
        user1.setName("First");
        user2.setUserId(2);
        user2.setName("Second");

        group1.setGroupId(3);
        group1.setGroupName("Group1");
        group2.setGroupId(4);
        group2.setGroupName("Group2");

        member1.setUser(user1);
        member1.setGroup(group1);
        member1.setUserRole("TestRole1");
        member1.setAdmin(true);

        member2.setUser(user2);
        member2.setGroup(group2);
    }

    @Test
    void toDTO() {
        MemberDTO memberDTO = memberMapper.toDTO(member1);
        assertEquals(Member.getGroupRoleOptions(), memberDTO.getGroupRoleOptions());
        assertEquals(user1.getUserId(), memberDTO.getUserId());
        assertEquals(user1.getName(), memberDTO.getUserName());
        assertEquals(group1.getGroupId(), memberDTO.getGroupId());
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