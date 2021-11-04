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

    @Query(value = "SELECT task_list_id FROM group_has_users g LEFT JOIN task_list t " +
                    "ON g.user_user_id = t.client_user_id " +
                    "WHERE user_role = 'client' AND group_group_id = :groupId",
                    nativeQuery = true)
    List<Integer> findAllTaskListIdsByGroupId(@Param("groupId") Integer groupId);

}
