package nl.miwgroningen.se6.heartcoded.CaTo.repository;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Integer> {

    TaskList findByClient(User user);
}
