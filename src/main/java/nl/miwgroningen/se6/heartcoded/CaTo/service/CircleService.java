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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.aspectj.bridge.MessageUtil.fail;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the circle repository and gives it to the controllers.
 */

@Service
public class CircleService {

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
                        .getResourceAsStream("static/css/images/Default-Group-Picture.png");

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

    public List<CircleClientDTO> findWithNameContains(String keyword) {
        List<CircleClientDTO> listFindByClientName = findWithClientName(keyword);
        List<CircleClientDTO> result = findCircleWithName(keyword);

        if (!keyword.isEmpty() && !listFindByClientName.isEmpty()) {
            for (CircleClientDTO resultDTO : result) {
                for (CircleClientDTO DTOFromClientName : listFindByClientName) {
                    if (!resultDTO.getCircleId().equals(DTOFromClientName.getCircleId())) {
                        result.add(DTOFromClientName);
                    }
                }
            }
        }
        return result;
    }

    private List<CircleClientDTO> findWithClientName(String keyword) {
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

        List<CircleClientDTO> result = new ArrayList<>();
        if(!clientList.isEmpty()) {
            for (Member client : clientList) {
                Integer circleId = client.getCircle().getCircleId();
                List<MemberDTO> clientDTOList = getClientsByCircleId(circleId);
                if (!result.contains(clientDTOList)) {
                    result.add(new CircleClientDTO(circleId, client.getCircle().getCircleName(), clientDTOList));
                }
            }
        }
        return result;
    }

    private List<MemberDTO> getClientsByCircleId(Integer circleId) {
        return memberMapper.toDTO(
                memberRepository.getMemberByCircle_CircleIdAndUserRoleContains(circleId, "Client"));
    }

    private List<CircleClientDTO> findCircleWithName(String keyword) {
        List<Circle> circleList = circleRepository.findByCircleNameContains(keyword);

        List<CircleClientDTO> result = new ArrayList<>();
        for (Circle circle : circleList) {
            List<MemberDTO> clientDTOList = getClientsByCircleId(circle.getCircleId());
            result.add(new CircleClientDTO(circle.getCircleId(), circle.getCircleName(), clientDTOList));
        }
        return result;
    }
}
