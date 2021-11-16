package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Maps the member model to a member DTO and back.
 */

@Component
public class MemberMapper {

    public MemberDTO toDTO(Member member) {
        MemberDTO result = new MemberDTO();

        result.setCircleRoleOptions(Member.getCircleRoleOptions());
        result.setUserId(member.getUser().getUserId());
        result.setUserName(member.getUser().getName());
        result.setCircleId(member.getCircle().getCircleId());
        result.setRole(member.getUserRole());
        result.setAdmin(member.isAdmin());

        return result;
    }

    public List<MemberDTO> toDTO(List<Member> memberList) {
        return memberList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<MemberDTO> toDTO(Optional<Member> member) {
        Optional<MemberDTO> result = Optional.empty();
        if(member.isPresent()) {
            result = Optional.of(toDTO(member.get()));
        }
        return result;
    }

    public Member toMember(MemberDTO memberDTO) {
        Member result = new Member();
        result.setUserRole(memberDTO.getRole());
        result.setAdmin(memberDTO.isAdmin());

        return result;
    }
}
