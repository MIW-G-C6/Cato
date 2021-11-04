package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberSiteAdminDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberSiteAdminMapperTest {

    private MemberSiteAdminMapper memberSiteAdminMapper;
    @BeforeEach
    void setUp() {
        memberSiteAdminMapper = new MemberSiteAdminMapper();
    }

    @Test
    void toDTO() {
        Group testGroup = new Group();
        testGroup.setGroupId(1);
        testGroup.setGroupName("TESTGROUP");

        User testUser = new User();
        testUser.setUserId(2);
        testUser.setName("TESTNAME");

        Member testMember = new Member();
        testMember.setAdmin(true);
        testMember.setGroup(testGroup);
        testMember.setUser(testUser);

        MemberSiteAdminDTO memberSiteAdminDTO = memberSiteAdminMapper.toDTO(testMember);

        assertEquals(1, memberSiteAdminDTO.getGroupId());
        assertEquals("TESTGROUP", memberSiteAdminDTO.getGroupName());
        assertEquals(2, memberSiteAdminDTO.getUserId());
        assertEquals("TESTNAME", memberSiteAdminDTO.getUserName());
    }
}