package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.*;


/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Users in a specific groups that get a specific role for that group
 */

@Entity
@IdClass(GroupHasUsersId.class)
public class GroupHasUsers {

    private static final String[] GROUP_ROLE_OPTIONS = {"Caregiver", "Client"};

    @Id
    @ManyToOne
    private Group group;

    @Id
    @ManyToOne
    private User user;

    private String userRole;

    private boolean isAdmin;

    public GroupHasUsers(Group group, User user, String userRole, boolean isAdmin) {
        this.group = group;
        this.user = user;
        this.userRole = userRole;
        this.isAdmin = isAdmin;
    }

    public GroupHasUsers() {
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
