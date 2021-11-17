package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.CircleDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.CircleMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CircleMapperTest {

    private CircleMapper circleMapper;

    @BeforeEach
    void setUp() {
        circleMapper = new CircleMapper();
    }

    @Test
    void circleToDTOTest() {
        Circle testCircle = new Circle();
        testCircle.setCircleId(1);
        testCircle.setCircleName("TESTCIRCLE");
        testCircle.setMemberList(new ArrayList<>());

        CircleDTO circleDTO = circleMapper.toDTO(testCircle);

        assertEquals("TESTCIRCLE", circleDTO.getCircleName());
        assertEquals(1, circleDTO.getCircleId());
    }

    @Test
    void circleDTOToCircleTest() {
        CircleDTO circleDTO = new CircleDTO();
        circleDTO.setCircleId(1);
        circleDTO.setCircleName("TESTCIRCLE");

        Circle circle = circleMapper.toCircle(circleDTO);

        Assertions.assertEquals("TESTCIRCLE", circle.getCircleName());
        Assertions.assertEquals(1, circle.getCircleId());
    }
}