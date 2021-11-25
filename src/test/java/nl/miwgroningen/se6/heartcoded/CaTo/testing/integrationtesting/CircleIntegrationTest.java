package nl.miwgroningen.se6.heartcoded.CaTo.testing.integrationtesting;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.CircleDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.CircleRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.CircleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 * <p>
 * Test the integration of a circle with the database
 */

@SpringBootTest
public class CircleIntegrationTest {

    private CircleService circleService;
    private CircleRepository circleRepository;

    @Autowired
    public CircleIntegrationTest(CircleService circleService, CircleRepository circleRepository) {
        this.circleService = circleService;
        this.circleRepository = circleRepository;
    }

    @Test
    void createCircleTest() {
        CircleDTO testCircleCreate = new CircleDTO();
        testCircleCreate.setCircleName("TEST_CIRCLE");

        circleService.saveCircle(testCircleCreate);
        System.out.println(testCircleCreate.getCircleId());

        List<Circle> found = circleRepository.findByCircleNameContains("TEST_CIRCLE");

        assertFalse(found.isEmpty());
        assertEquals("TEST_CIRCLE", found.get(0).getCircleName());

        circleService.deleteCircleById(found.get(0).getCircleId());

        Optional<Circle> testCircleDelete = circleRepository.findById(found.get(0).getCircleId());

        assertTrue(testCircleDelete.isEmpty());
    }
}
