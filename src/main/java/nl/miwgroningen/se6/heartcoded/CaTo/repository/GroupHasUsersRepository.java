package nl.miwgroningen.se6.heartcoded.CaTo.repository;

import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupHasUsersRepository extends JpaRepository <GroupHasUsers, Integer> {
}
