package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberSiteAdminDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import org.springframework.stereotype.Component;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Maps Member models to MemberSiteAdminDTO's and back
 */
@Component
public class MemberSiteAdminMapper {

    public MemberSiteAdminDTO toDTO(Member member) {
        MemberSiteAdminDTO memberSiteAdminDTO = new MemberSiteAdminDTO();
        memberSiteAdminDTO.setGroupId(member.getGroup().getGroupId());
        memberSiteAdminDTO.setGroupName(member.getGroup().getGroupName());
        memberSiteAdminDTO.setUserId(member.getUser().getUserId());
        memberSiteAdminDTO.setUserName(member.getUser().getName());
        return  memberSiteAdminDTO;
    }
}
