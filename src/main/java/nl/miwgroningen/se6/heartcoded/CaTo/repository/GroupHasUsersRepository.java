package nl.miwgroningen.se6.heartcoded.CaTo.repository;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupHasUsersRepository extends JpaRepository <GroupHasUsers, Integer> {

    List<GroupHasUsers> getAllByGroup(Group group);

    void deleteByUserAndGroup(User user, Group group);

    GroupHasUsers getGroupHasUsersByUserAndGroup(User user, Group group);

    Optional<GroupHasUsers> findGroupHasUsersByUserAndGroup(User user, Group group);

}
