package nl.miwgroningen.se6.heartcoded.CaTo.repository;

import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskListRepository extends JpaRepository<TaskList, Integer> {

    TaskList getByGroupGroupId(Integer groupId);

    Optional<TaskList> findByGroupGroupId(Integer groupId);
}
