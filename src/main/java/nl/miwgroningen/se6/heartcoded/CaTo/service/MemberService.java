package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.GroupMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the member repository and gives it to the controllers.
 */

@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    public MemberService(MemberMapper memberMapper, GroupMapper groupMapper, GroupRepository groupRepository,
                         UserRepository userRepository, MemberRepository memberRepository) {
        this.memberMapper = memberMapper;
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
    }

    public void saveMember(MemberDTO memberDTO) {
        Member member = memberMapper.toMember(memberDTO);
        member.setUser(userRepository.getById(memberDTO.getUserId()));
        member.setGroup(groupRepository.getById(memberDTO.getGroupId()));
        member.setAdmin(memberDTO.isAdmin());
        memberRepository.save(member);
    }
//
//    public List<MemberDTO> getAllByGroupId(Integer groupId) {
//        return memberMapper.toDTO(memberRepository.getAllByGroup(groupRepository.getById(groupId)));
//    }

    @Transactional
    public void deleteByUserId(Integer userId, Integer groupId) {
        memberRepository.deleteByUserAndGroup(userRepository.getById(userId), groupRepository.getById(groupId));
    }

    public Optional<MemberDTO> findByUserIdAndGroupId(Integer userId, Integer groupId) {
        Optional<MemberDTO> result = Optional.empty();
        Optional<Member> member = memberRepository.findMemberByUserAndGroup(
                userRepository.getById(userId),
                groupRepository.getById(groupId));
        if(member.isPresent()) {
            result = memberMapper.toDTO(member);
        }
        return result;
    }

    public List<GroupDTO> getAllGroupsByUserId(Integer userId) {
        List<GroupDTO> result = new ArrayList<>();
        List<Member> groupsHasUserList = memberRepository.getAllByUser(userRepository.getById(userId));
        for (Member member : groupsHasUserList) {
            result.add(groupMapper.toDTO(member.getGroup()));
        }
        return result;
    }

    public List<MemberDTO> findAllClients() {
        List<MemberDTO> result = new ArrayList<>();
        for (Member member : memberRepository.findAll()) {
            if (member.getUserRole().equals("Client")) {
                result.add(memberMapper.toDTO(member));
            }
        }
        return result;
    }

    public MemberDTO getByClient(UserDTO client) {
        List<Member> allGroupHasUsers = memberRepository.getAllByUser(
                userRepository.getById(client.getUserId()));

        MemberDTO result = new MemberDTO();
        for (Member member : allGroupHasUsers) {
            if (member.getUserRole().equals("Client")) {
                result = memberMapper.toDTO(member);
            }
        }
        return result;
    }

    public boolean userInGroupExists(MemberDTO member) {
        if (findByUserIdAndGroupId(member.getUserId(), member.getGroupId()).isPresent()) {
                return true;
            }
        return false;
    }

    public List<MemberDTO> getGroupAdminsByGroupId(Integer groupId) {
        List<MemberDTO> allMembersInGroup = memberMapper.toDTO(memberRepository.getAllByGroupId(groupId));
        List<MemberDTO> result = new ArrayList<>();

        for (MemberDTO member : allMembersInGroup) {
            if (member.isAdmin()) {
                result.add(member);
            }
        }
        return result;
    }

    public boolean findOutIfMemberIsAdmin(MemberDTO memberDTO) {
        Optional<MemberDTO> memberOptional = findByUserIdAndGroupId(memberDTO.getUserId(), memberDTO.getGroupId());
        if (memberOptional.isPresent()) {
            Member member = memberMapper.toMember(memberOptional.get());
            return member.isAdmin();
        }
        return false;
    }

    public boolean isClientInOtherGroup(UserDTO user, Integer groupId) {
        boolean result = false;
        List<MemberDTO> allClients = findAllClients();

        for (MemberDTO client : allClients) {
            if (Objects.equals(user.getUserId(), client.getUserId()) &&
                    !Objects.equals(client.getGroupId(), groupId)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
