package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.GroupDTOConverter;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.GroupHasUsersDTOConverter;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.UserDTOConverter;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 * <p>
 * Dit is wat het programma doet
 */

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TaskListRepository taskListRepository;

    public MemberService(MemberMapper memberMapper, GroupRepository groupRepository, UserRepository userRepository,
                         MemberRepository memberRepository, TaskListRepository taskListRepository) {
        this.memberMapper = memberMapper;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.taskListRepository = taskListRepository;
    }

    public void saveMember(MemberDTO memberDTO) {
        Member member = memberMapper.toMember(memberDTO);
        member.setUser(userRepository.getById(memberDTO.getUserId()));
        //TODO
        memberRepository.save(member);
    }

    public List<GroupHasUsersDTO> getAllByGroupId(Integer groupId) {
        return groupHasUsersDTOConverter.toDTOList(memberRepository.
                getAllByGroup(groupRepository.getById(groupId)));
    }

    @Transactional
    public void deleteByUserId(Integer userId, Integer groupId) {
        memberRepository.deleteByUserAndGroup(userRepository.getById(userId), groupRepository.getById(groupId));
    }

    public Optional<GroupHasUsersDTO> findByUserIdAndGroupId(Integer userId, Integer groupId) {
        Optional<GroupHasUsersDTO> result = Optional.empty();
        Optional<Member> groupHasUsers = memberRepository.findGroupHasUsersByUserAndGroup(
                userRepository.getById(userId),
                groupRepository.getById(groupId));
        if(groupHasUsers.isPresent()) {
            result = Optional.of(groupHasUsersDTOConverter.toDTO(groupHasUsers.get()));
        }
        return result;
    }

    public List<GroupDTO> getAllGroupsByUserId(Integer userId) {
        List<GroupDTO> result = new ArrayList<>();
        List<Member> groupsHasUserList = memberRepository.getAllByUser(userRepository.getById(userId));
        for (Member member : groupsHasUserList) {
            result.add(groupDTOConverter.toDTO(member.getGroup()));
        }
        return result;
    }

    public List<GroupHasUsersDTO> findAllClients() {
        List<GroupHasUsersDTO> result = new ArrayList<>();
        for (Member member : memberRepository.findAll()) {
            if (member.getUserRole().equals("Client")) {
                result.add(groupHasUsersDTOConverter.toDTO(member));
            }
        }
        return result;
    }

    public GroupHasUsersDTO getByClient(UserDTO client) {
        List<Member> allGroupHasUsers = memberRepository.getAllByUser(
                userRepository.getById(client.getUserId()));

        GroupHasUsersDTO result = new GroupHasUsersDTO();
        for (Member member : allGroupHasUsers) {
            if (member.getUserRole().equals("Client")) {
                result = groupHasUsersDTOConverter.toDTO(member);
            }
        }
        return result;
    }

    public boolean userInGroupExists(GroupHasUsersDTO member) {
        if (findByUserIdAndGroupId(
                member.getUser().getUserId(),
                member.getGroup().getGroupId())
                .isPresent()){
                return true;
            }
        return false;
    }

    public List<GroupHasUsersDTO> getGroupAdminsByGroupId(Integer groupId) {
        List<GroupHasUsersDTO> allFromGroup = getAllByGroupId(groupId);
        List<GroupHasUsersDTO> result = new ArrayList<>();

        for (GroupHasUsersDTO groupHasUsers : allFromGroup) {
            if (groupHasUsers.isAdmin()) {
                result.add(groupHasUsers);
            }
        }
        return result;
    }

    public boolean findOutIfMemberIsAdmin(GroupHasUsersDTO member) {
        return findByUserIdAndGroupId(member.getUser().getUserId(),
                member.getGroup().getGroupId()).get().isAdmin();
    }

    public boolean isClientInOtherGroup(UserDTO user, Integer groupId) {
        boolean result = false;
        List<GroupHasUsersDTO> allClients = findAllClients();

        for (GroupHasUsersDTO client : allClients) {
            if (Objects.equals(user.getUserId(), client.getUser().getUserId()) && !Objects.equals(client.getGroup().getGroupId(), groupId)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
