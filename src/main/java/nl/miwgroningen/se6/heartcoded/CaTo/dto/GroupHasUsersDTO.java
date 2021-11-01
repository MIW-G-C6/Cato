package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 * <p>
 * Dit is wat het programma doet
 */

public class GroupHasUsersDTO {

    private static final String[] GROUP_ROLE_OPTIONS = {"Caregiver", "Client"};

    @Id
    @ManyToOne
    private Group group;

    @Id
    @ManyToOne
    private User user;

    private String userRole;

    private boolean isAdmin;

    public GroupHasUsersDTO(Group group, User user, String userRole, boolean isAdmin) {
        this.group = group;
        this.user = user;
        this.userRole = userRole;
        this.isAdmin = isAdmin;
    }

    public GroupHasUsersDTO() {
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public static String[] getGroupRoleOptions() {
        return GROUP_ROLE_OPTIONS;
    }
}
