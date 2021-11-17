package nl.miwgroningen.se6.heartcoded.CaTo.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.CircleDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Maps CircleDTO to Circle entities and back.
 */

@Component
public class CircleMapper {

    public CircleDTO toDTO(Circle circle) {
        CircleDTO circleDTO = new CircleDTO();
        circleDTO.setCircleId(circle.getCircleId());
        circleDTO.setCircleName(circle.getCircleName());

        return circleDTO;
    }

    public List<CircleDTO> toDTO(List<Circle> circleList) {
        return circleList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Circle toCircle(CircleDTO circleDTO) {
        Circle circle = new Circle();
        circle.setCircleId(circleDTO.getCircleId());
        circle.setCircleName(circleDTO.getCircleName());

        return circle;
    }
}

