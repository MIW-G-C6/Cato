package nl.miwgroningen.se6.heartcoded.CaTo.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberWithProfilePicDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import org.springframework.stereotype.Component;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Maps the member model to a member DTO and back.
 */

@Component
public class MemberWithProfilePicMapper {

    public MemberWithProfilePicDTO toDTO(Member member, String profilePicture) {
        MemberWithProfilePicDTO result = new MemberWithProfilePicDTO();
        result.setUserId(member.getUser().getUserId());
        result.setUserName(member.getUser().getName());
        result.setCircleId(member.getCircle().getCircleId());
        result.setRole(member.getUserRole());
        result.setAdmin(member.isAdmin());
        result.setProfilePicture(profilePicture);
        return result;
    }
}
