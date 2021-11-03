package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberRoleDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import org.springframework.stereotype.Component;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 * hier komt wat het programma doet
 */

@Component
public class MemberRoleMapper {

    public MemberRoleDTO toDTO(Member member) {
        MemberRoleDTO result = new MemberRoleDTO();
        result.setAdmin(member.isAdmin());
        result.setUserRole(member.getUserRole());
        result.setGroupRoleOptions(member.getGroupRoleOptions());
        return result;
    }

    //TODO check if to Member model is necessary
}
