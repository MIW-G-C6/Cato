
package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.*;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.*;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.CircleRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the member repository and gives it to the controllers.
 */

@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final MemberWithProfilePicMapper memberWithProfilePicMapper;
    private final CircleMapper circleMapper;
    private final UserMapper userMapper;

    private final MemberSiteAdminMapper memberSiteAdminMapper;
    private final CircleRepository circleRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    public MemberService(MemberMapper memberMapper,
                         MemberWithProfilePicMapper memberWithProfilePicMapper,
                         CircleMapper circleMapper,
                         MemberSiteAdminMapper memberSiteAdminMapper,
                         CircleRepository circleRepository,
                         UserRepository userRepository,
                         MemberRepository memberRepository,
                         UserMapper userMapper) {
        this.memberMapper = memberMapper;
        this.memberWithProfilePicMapper = memberWithProfilePicMapper;
        this.circleMapper = circleMapper;
        this.memberSiteAdminMapper = memberSiteAdminMapper;
        this.circleRepository = circleRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.userMapper = userMapper;
    }

    public List<MemberDTO> findAllMembers() {
        List<MemberDTO> result = memberMapper.toDTO(memberRepository.findAll());
        sortMemberDTOOnRoleAndAlphabet(result);
        return result;
    }

    public Integer totalNumberOfClients() {
        return memberRepository.countAllByUserRoleIs("Client");
    }

    public void saveMember(MemberDTO memberDTO) {
        Member member = memberMapper.toMember(memberDTO);
        member.setUser(userRepository.getById(memberDTO.getUserId()));
        member.setCircle(circleRepository.getById(memberDTO.getCircleId()));
        member.setAdmin(memberDTO.isAdmin());

        memberRepository.save(member);
    }

    public List<MemberDTO> getAllByCircleId(Integer circleId) {
        List<MemberDTO> result = memberMapper.toDTO(memberRepository.getAllByCircleCircleId(circleId));
        sortMemberDTOOnRoleAndAlphabet(result);
        return result;
    }

    @Transactional
    public void deleteByUserId(Integer userId, Integer cirlceId) {
        memberRepository.deleteByUserAndCircle(userRepository.getById(userId), circleRepository.getById(cirlceId));
    }

    public Optional<MemberDTO> findByUserIdAndCircleId(Integer userId, Integer circleId) {
        Optional<MemberDTO> result = Optional.empty();
        Optional<Member> member = memberRepository.findMemberByUserAndCircle(
                userRepository.getById(userId),
                circleRepository.getById(circleId));

        if(member.isPresent()) {
            result = memberMapper.toDTO(member);
        }

        return result;
    }

    public List<CircleDTO> getAllCirclesByUserId(Integer userId) {
        List<CircleDTO> result = new ArrayList<>();
        List<Member> memberList = memberRepository.getAllByUser(userRepository.getById(userId));

        for (Member member : memberList) {
            result.add(circleMapper.toDTO(member.getCircle()));
        }

        return result;
    }

    public List<MemberDTO> findAllCaregiversByCircleId(Integer circleId) {
        List<MemberDTO> result = new ArrayList<>();

        for (Member member : memberRepository.getAllByCircleCircleId(circleId)) {
            if(member.getUserRole().equals("Caregiver")) {
                result.add(memberMapper.toDTO(member));
            }
        }
        sortMemberDTOOnRoleAndAlphabet(result);
        return result;
    }

    public List<MemberWithProfilePicDTO> findAllCaregiversAndPhotoByCircleId(Integer circleId) {
        List<Member> memberList =
                memberRepository.getMemberByCircle_CircleIdAndUserRoleContains(circleId, "Caregiver");
        return getMemberWithProfileFromMemberList(memberList);
    }

    public List<MemberWithProfilePicDTO> findAllClientsAndPhotoByCircleId(Integer circleId) {
        List<Member> memberList =
                memberRepository.getMemberByCircle_CircleIdAndUserRoleContains(circleId, "Client");
        return getMemberWithProfileFromMemberList(memberList);
    }

    private List<MemberWithProfilePicDTO> getMemberWithProfileFromMemberList(List<Member> memberList) {
        List<MemberWithProfilePicDTO> result = new ArrayList<>();
        for (Member member : memberList) {
            UserDTO thisUser = userMapper.toDTO(userRepository.getById(member.getUser().getUserId()));

            String profilePicture = Base64.getEncoder().encodeToString(thisUser.getProfilePicture());

            result.add(memberWithProfilePicMapper.toDTO(member, profilePicture));
        }
        sortMemberWithProfilePicDTOOnRoleAndAlphabet(result);
        return result;
    }

    public List<MemberDTO> findAllClients() {
        List<MemberDTO> result = new ArrayList<>();

        for (Member member : memberRepository.findAll()) {
            if (member.getUserRole().equals("Client")) {
                result.add(memberMapper.toDTO(member));
            }
        }
        sortMemberDTOOnRoleAndAlphabet(result);
        return result;
    }

    public List<MemberDTO> findAllClientsInCircle(Integer circleId) {
        List<MemberDTO> result = new ArrayList<>();
        List<MemberDTO> memberList = getAllByCircleId(circleId);

        for (MemberDTO member : memberList) {
            if(member.getRole().equals("Client")) {
                result.add(member);
            }
        }
        sortMemberDTOOnRoleAndAlphabet(result);
        return result;
    }

    public List<MemberSiteAdminDTO> findAllClientsForSiteAdmin() {
        List<MemberSiteAdminDTO> result = new ArrayList<>();

        for (Member member : memberRepository.findAll()) {
            if (member.getUserRole().equals("Client")) {
                result.add(memberSiteAdminMapper.toDTO(member));
            }
        }

        return result;
    }

    public boolean userInCircleExists(MemberDTO member) {
        if (findByUserIdAndCircleId(member.getUserId(), member.getCircleId()).isPresent()) {
            return true;
        }

        return false;
    }

    public List<MemberDTO> getCircleAdminsByCircleId(Integer circleId) {
        List<MemberDTO> allMembersInCircle = memberMapper.toDTO(memberRepository.getAllByCircleCircleId(circleId));
        List<MemberDTO> result = new ArrayList<>();

        for (MemberDTO member : allMembersInCircle) {
            if (member.isAdmin()) {
                result.add(member);
            }
        }
        sortMemberDTOOnRoleAndAlphabet(result);
        return result;
    }

    public boolean findOutIfMemberIsAdmin(MemberDTO memberDTO) {
        Optional<MemberDTO> memberOptional = findByUserIdAndCircleId(memberDTO.getUserId(), memberDTO.getCircleId());

        return memberOptional.map(MemberDTO::isAdmin).orElse(false);
    }

    public boolean isClientInOtherCircle(Integer userid, Integer circleId) {
        boolean result = false;
        List<MemberDTO> allClients = findAllClients();

        for (MemberDTO client : allClients) {
            if (Objects.equals(userid, client.getUserId()) &&
                    !Objects.equals(client.getCircleId(), circleId)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean isClient(MemberDTO member) {
        return member.getRole().equals(member.getCircleRoleOptions()[1]);
    }

    public boolean userIsMemberOfCircle(Integer circleId) {
        Integer userId = getCurrentUser().getUserId();
        List<Member> memberList = memberRepository.getAllByCircleCircleId(circleId);

        for (Member member : memberList) {
            if (Objects.equals(userId, member.getUser().getUserId())) {
                return true;
            }
        }
        return false;
    }

    public boolean userIsCircleAdmin(Integer circleId, Integer userId) {
        Optional<Member> member = memberRepository.findMemberByUserUserIdAndCircleCircleId(userId, circleId);

        if (member.isPresent()) {
            return member.get().isAdmin();
        }
        return false;
    }

    public boolean userIsLastCircleAdminInAnyCircle(Integer userId) {
        List<Member> allMembers = memberRepository.findAllByUserUserId(userId);

        for (Member member : allMembers) {
            if (getCircleAdminsByCircleId(member.getCircle().getCircleId()).size() == 1
                    && member.isAdmin()) {
                return true;
            }
        }
        return false;
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<CircleDTO> allCirclesByUserIdWithAdminCheck(Integer userId) {
        List<CircleDTO> result = new ArrayList<>();
        List<CircleDTO> temp = getAllCirclesByUserId(userId);

        for (CircleDTO circleDTO : temp) {
            if (userIsCircleAdmin(circleDTO.getCircleId(), userId)) {
                circleDTO.setUserIsCircleAdmin(true);
            }
            result.add(circleDTO);
        }

        return result;
    }

    private List<Integer> getLastThreeCircleIdByUserId(Integer userId) {
        User user = userRepository.getById(userId);
        List<Integer> result = new ArrayList<>();
        result.add(0, user.getCircleOne());
        result.add(1, user.getCircleTwo());
        result.add(2, user.getCircleThree());

        return result;
    }

    public List<CircleDTO> getLastThreeCirclesByUserId(Integer userId) {
        List<CircleDTO> result = new ArrayList<>();
        List<Integer> circleList = getLastThreeCircleIdByUserId(userId);

        for (Integer circleId : circleList) {
            if(!(circleId == 0)) {
                CircleDTO circleDTO = circleMapper.toDTO(circleRepository.getById(circleId));
                if (userIsCircleAdmin(circleDTO.getCircleId(), userId)) {
                    circleDTO.setUserIsCircleAdmin(true);
                }
                result.add(circleDTO);
            } else {
                CircleDTO circleZero = new CircleDTO();
                circleZero.setCircleId(0);
                circleZero.setCircleName("Non-existing circle");
                result.add(circleZero);
            }
        }

        return result;
    }

    public void addCircleToLastThreeCircles(Integer circleId) {
        User user = userRepository.getById(getCurrentUser().getUserId());
        List<Integer> lastThreeCircleList = getLastThreeCircleIdByUserId(user.getUserId());

        if(lastThreeCircleList.contains(circleId)) {
            if (lastThreeCircleList.get(1).equals(circleId)) {
                user.setCircleTwo(lastThreeCircleList.get(0));
                user.setCircleOne(circleId);
            } else if (lastThreeCircleList.get(2).equals(circleId)) {
                user.setCircleThree(lastThreeCircleList.get(1));
                user.setCircleTwo(lastThreeCircleList.get(0));
                user.setCircleOne(circleId);
            }
        } else {
            user.setAllThreeCircles(lastThreeCircleList, circleId);
        }

        userRepository.save(user);
    }

    public void ifCircleIsDeletedSetCircles(User user, Integer circleId) {
        if(user.getCircleOne() == circleId) {
            user.setCircleOne(user.getCircleTwo());
            user.setCircleTwo(user.getCircleThree());
            user.setCircleThree(0);
        } else if(user.getCircleTwo() == circleId) {
            user.setCircleTwo(user.getCircleThree());
            user.setCircleThree(0);
        } else if(user.getCircleThree() == circleId) {
            user.setCircleThree(0);
        }
    }

    public void checkForCircleDeletion() {
        User user = userRepository.getById(getCurrentUser().getUserId());
        List<Integer> circleList = getLastThreeCircleIdByUserId(user.getUserId());

        for (Integer circleId : circleList) {
            Optional<Circle> circle = circleRepository.findById(circleId);
            if (circle.isEmpty() || !userIsMemberOfCircle(circleId)) {
                ifCircleIsDeletedSetCircles(user, circleId);
            }
        }

        userRepository.save(user);
    }

    private void sortMemberWithProfilePicDTOOnRoleAndAlphabet(List<MemberWithProfilePicDTO> list) {
        list.sort(Comparator.comparing(MemberWithProfilePicDTO::isAdmin).reversed()
                .thenComparing(MemberWithProfilePicDTO::getUserName));
    }

    private void sortMemberDTOOnRoleAndAlphabet(List<MemberDTO> list) {
        list.sort(Comparator.comparing(MemberDTO::isAdmin).reversed()
                .thenComparing(MemberDTO::getRole).thenComparing(MemberDTO::getUserName));
    }
}