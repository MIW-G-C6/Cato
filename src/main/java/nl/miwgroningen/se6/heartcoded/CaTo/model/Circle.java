package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * A care circle which contains multiple regisered users with at least one client.
 */

@Entity
public class Circle {

    @Id
    @GeneratedValue
    private Integer circleId;

    private String circleName;

    @OneToMany(mappedBy = "circle", cascade = CascadeType.ALL)
    private List<Member> memberList;

    public Circle(Integer circleId, String circleName, List<Member> memberList) {
        this.circleId = circleId;
        this.circleName = circleName;
        this.memberList = memberList;
    }

    public Circle() {
    }

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }
}