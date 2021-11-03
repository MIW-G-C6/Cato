package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import org.springframework.stereotype.Component;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Maps the member model to a member DTO and back.
 */

@Component
public class MemberMapper {

    public MemberDTO toDTO(Member member) {
        MemberDTO result = new MemberDTO();

        result.setUserId(member.getUser().getUserId());
        result.setUserName(member.getUser().getUsername());
        result.setRole(member.getUserRole());

        return result;
    }

    public Member toMember(MemberDTO memberDTO) {
        Member result = new Member();
        result.setUserRole(memberDTO.getRole());

        return result;
    }
}
