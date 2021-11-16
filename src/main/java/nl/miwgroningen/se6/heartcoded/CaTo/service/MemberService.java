package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.*;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.CircleRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers.CircleMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers.MemberMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers.MemberSiteAdminMapper;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final CircleMapper circleMapper;
    private final MemberSiteAdminMapper memberSiteAdminMapper;
    private final CircleRepository circleRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    public MemberService(MemberMapper memberMapper, CircleMapper circleMapper,
                         MemberSiteAdminMapper memberSiteAdminMapper, CircleRepository circleRepository,
                         UserRepository userRepository, MemberRepository memberRepository) {
        this.memberMapper = memberMapper;
        this.circleMapper = circleMapper;
        this.memberSiteAdminMapper = memberSiteAdminMapper;
        this.circleRepository = circleRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
    }

    public List<MemberDTO> findAllMembers() {
        return memberMapper.toDTO(memberRepository.findAll());
    }

    public void saveMember(MemberDTO memberDTO) {
        Member member = memberMapper.toMember(memberDTO);
        member.setUser(userRepository.getById(memberDTO.getUserId()));
        member.setCircle(circleRepository.getById(memberDTO.getCircleId()));
        member.setAdmin(memberDTO.isAdmin());
        memberRepository.save(member);
    }

    public List<MemberDTO> getAllByCircleId(Integer circleId) {
        return memberMapper.toDTO(memberRepository.getAllByCircleCircleId(circleId));
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

    public List<MemberDTO> findAllClientsInCircle(Integer circleId) {
        List<MemberDTO> result = new ArrayList<>();
        List<MemberDTO> memberList = getAllByCircleId(circleId);
        for (MemberDTO member : memberList) {
            if(member.getRole().equals("Client")) {
                result.add(member);
            }
        }
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

    public boolean userIsCircleAdmin(Integer circleId) {
        Integer userId = getCurrentUser().getUserId();
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

    public List<CircleDTO> allCirclesByUserIdWithAdminCheck(Integer currentUserId) {
        List<CircleDTO> result = new ArrayList<>();
        List<CircleDTO> temp = getAllCirclesByUserId(currentUserId);
        for (CircleDTO circleDTO : temp) {
            if (userIsCircleAdmin(circleDTO.getCircleId())) {
                circleDTO.setCurrentUserIsCircleAdmin(true);
            }
            result.add(circleDTO);
        }
        return result;
    }
}
