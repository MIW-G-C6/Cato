package nl.miwgroningen.se6.heartcoded.CaTo.repository;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CircleRepository extends JpaRepository<Circle, Integer> {

    @Override
    Optional<Circle> findById(Integer circleId);

    List<Circle> findByCircleNameContains(String circleName);
}
