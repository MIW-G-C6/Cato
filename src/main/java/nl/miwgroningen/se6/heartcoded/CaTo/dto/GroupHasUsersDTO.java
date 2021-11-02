package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * this is a DTO model based on the GroupHasUsers model
 */

public class GroupHasUsersDTO {

    private static final String[] GROUP_ROLE_OPTIONS = {"Caregiver", "Client"};

    private GroupDTO group;

    private UserDTO user;

    private String userRole;

    private boolean isAdmin;

    public GroupDTO getGroup() {
        return group;
    }

    public GroupHasUsersDTO(GroupDTO group, UserDTO user, String userRole, boolean isAdmin) {
        this.group = group;
        this.user = user;
        this.userRole = userRole;
        this.isAdmin = isAdmin;
    }

    public GroupHasUsersDTO() {
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
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
