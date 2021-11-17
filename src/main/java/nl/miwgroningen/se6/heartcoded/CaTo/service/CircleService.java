package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.CircleDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.CircleRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.CircleMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the circle repository and gives it to the controllers
 */

@Service
public class CircleService {

    private final MemberRepository memberRepository;
    private final CircleRepository circleRepository;
    private final CircleMapper circleMapper;

    public CircleService(MemberRepository memberRepository, CircleRepository circleRepository,
                         CircleMapper circleMapper) {
        this.memberRepository = memberRepository;
        this.circleRepository = circleRepository;
        this.circleMapper = circleMapper;
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
        Circle result = circleMapper.toCircle(circleDTO);
        result.setMemberList(memberRepository.getAllByCircleCircleId(result.getCircleId()));
        circleRepository.save(result);
        circleDTO.setCircleId(result.getCircleId());
    }

    public CircleDTO getById(Integer circleId) {
        return circleMapper.toDTO(circleRepository.getById(circleId));
    }

    public List<CircleDTO> findWithNameContains(String keyword) {
        return circleMapper.toDTO(circleRepository.findByCircleNameContains(keyword));
    }
}
