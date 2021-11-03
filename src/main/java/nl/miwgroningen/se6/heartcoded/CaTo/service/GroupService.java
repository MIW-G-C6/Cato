package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.GroupMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the group repository and gives it to the controllers
 */

@Service
public class GroupService {

    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupService(MemberRepository memberRepository, GroupRepository groupRepository, GroupMapper groupMapper) {
        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    public List<GroupDTO> findAllGroups() {
        List<Group> allGroups = groupRepository.findAll();
        List<GroupDTO> result = new ArrayList<>();
        for (Group group : allGroups) {
            result.add(groupMapper.toDTO(group));
        }
        return result;
    }

    public void deleteGroupById(Integer groupId) {
        groupRepository.deleteById(groupId);
    }

    public void saveGroup (GroupDTO groupDTO) {
        Group result = groupMapper.toGroup(groupDTO);
        result.setMemberList(memberRepository.getAllByGroupGroupId(result.getGroupId()));
        groupRepository.save(result);
    }

    public GroupDTO getById(Integer groupId) {
        return groupMapper.toDTO(groupRepository.getById(groupId));
    }
}
