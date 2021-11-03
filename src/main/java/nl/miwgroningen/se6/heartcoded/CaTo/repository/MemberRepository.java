package nl.miwgroningen.se6.heartcoded.CaTo.repository;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository <Member, Integer> {

    List<Member> getAllByGroup(Group group);

    void deleteByUserAndGroup(User user, Group group);

    Optional<Member> findMemberByUserAndGroup(User user, Group group);

    List<Member> getAllByUser(User user);

    List<Member> getAllByGroupGroupId(Integer groupId);
}
