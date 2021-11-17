package nl.miwgroningen.se6.heartcoded.CaTo.repository;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository <Member, Integer> {

    void deleteByUserAndCircle(User user, Circle circle);

    Optional<Member> findMemberByUserAndCircle(User user, Circle circle);

    List<Member> findAllByUserUserId(Integer userId);

    List<Member> getAllByUser(User user);

    List<Member> getAllByCircleCircleId(Integer circleId);

    Optional<Member> findMemberByUserUserIdAndCircleCircleId(Integer userId, Integer circleId);

    List<Member> getMemberByCircle_CircleIdAndUserRoleContains(Integer circleId, String client);

    Member getMemberByUserUserIdAndUserRoleContains(Integer userId, String client);
}
