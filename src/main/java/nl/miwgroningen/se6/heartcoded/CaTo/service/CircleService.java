package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.CircleClientDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.CircleDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.MemberMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.CircleRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.CircleMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.aspectj.bridge.MessageUtil.fail;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the circle repository and gives it to the controllers.
 */

@Service
public class CircleService {

    private static final String DEFAULT_GROUP_PICTURE_PATH = "static/css/images/Default-Group-Picture.png";
    private final MemberRepository memberRepository;
    private final CircleRepository circleRepository;
    private final UserRepository userRepository;

    private final CircleMapper circleMapper;
    private final MemberMapper memberMapper;

    public CircleService(MemberRepository memberRepository, CircleRepository circleRepository,
                         UserRepository userRepository, CircleMapper circleMapper, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.circleRepository = circleRepository;
        this.userRepository = userRepository;
        this.circleMapper = circleMapper;
        this.memberMapper = memberMapper;
    }

    public List<CircleDTO> findAllCircles() {
        List<Circle> allCircles = circleRepository.findAll();
        List<CircleDTO> result = new ArrayList<>();

        for (Circle circle : allCircles) {
            result.add(circleMapper.toDTO(circle));
        }

        return result;
    }

    public Long totalNumberOfCircles() {
        return circleRepository.count();
    }

    public void deleteCircleById(Integer circleId) {
        circleRepository.deleteById(circleId);
    }

    public void saveCircle(CircleDTO circleDTO) {
        if (circleDTO.getCircleId() == null) {
            try {
                InputStream inputStream = getClass()
                        .getClassLoader()
                        .getResourceAsStream(DEFAULT_GROUP_PICTURE_PATH);

                if (inputStream == null) {
                    fail("Unable to find resource");
                } else {
                    circleDTO.setCirclePhoto(IOUtils.toByteArray(inputStream));
                }
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
        Circle result = circleMapper.toCircle(circleDTO);
        result.setMemberList(memberRepository.getAllByCircleCircleId(result.getCircleId()));

        circleRepository.save(result);
        circleDTO.setCircleId(result.getCircleId());
    }

    public CircleDTO getById(Integer circleId) {
        return circleMapper.toDTO(circleRepository.getById(circleId));
    }

    @Transactional
    public List<CircleClientDTO> findWithNameContains(String keyword) {
        List<Integer> findWithClientNameList = findWithClientName(keyword);
        List<Integer> findWithCircleNameList = findCircleWithName(keyword);
        List<Integer> totalList = new ArrayList<>();
        totalList.addAll(findWithClientNameList);
        totalList.addAll(findWithCircleNameList);

        Set<Integer> circleIdSet = new HashSet<>(totalList);
        totalList.clear();
        totalList.addAll(circleIdSet);

        List<CircleClientDTO> result = new ArrayList<>();

        for (Integer circleId : totalList) {
            List <MemberDTO> clientList = getClientsByCircleId(circleId);
            String circleName = circleRepository.getById(circleId).getCircleName();
            result.add(new CircleClientDTO(circleId, circleName, clientList));
            }

        return result;
    }

    private List<Integer> findWithClientName(String keyword) {
        List<Member> clientList = new ArrayList<>();
        List<User> userList = userRepository.findByNameContains(keyword);

        for (User user : userList) {
            List<Member> memberListOfThisUser = memberRepository.findAllByUserUserId(user.getUserId());
            for (Member member : memberListOfThisUser) {
                if (member.getUserRole().equals("Client")){
                    clientList.add(member);
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        if(!clientList.isEmpty()) {
            for (Member client : clientList) {
                result.add(client.getCircle().getCircleId());
            }
        }

        Set<Integer> circleClientDTOSet = new HashSet<>(result);
        result.clear();
        result.addAll(circleClientDTOSet);

        return result;
    }

    private List<MemberDTO> getClientsByCircleId(Integer circleId) {
        return memberMapper.toDTO(
                memberRepository.getMemberByCircle_CircleIdAndUserRoleContains(circleId, "Client"));
    }

    private List<Integer> findCircleWithName(String keyword) {
        List<Circle> circleList = circleRepository.findByCircleNameContains(keyword);
        List<Integer> result = new ArrayList<>();
        for (Circle circle : circleList) {
            result.add(circle.getCircleId());
        }
        return result;
    }
}
