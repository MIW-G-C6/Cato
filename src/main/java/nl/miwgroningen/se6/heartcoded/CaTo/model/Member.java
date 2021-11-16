package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.*;


/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Users in a specific circle that get a specific role for that circle
 */

@Entity
@IdClass(MemberId.class)
public class Member {

    private static final String[] CIRCLE_ROLE_OPTIONS = {"Caregiver", "Client"};

    @Id
    @ManyToOne
    private Circle circle;

    @Id
    @ManyToOne
    private User user;

    private String userRole;

    private boolean isAdmin;

    public Member(Circle circle, User user, String userRole, boolean isAdmin) {
        this.circle = circle;
        this.user = user;
        this.userRole = userRole;
        this.isAdmin = isAdmin;
    }

    public Member() {
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public static String[] getCircleRoleOptions() {
        return CIRCLE_ROLE_OPTIONS;
    }
}
