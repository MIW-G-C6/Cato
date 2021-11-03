package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import java.util.List;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 * hier komt wat het programma doet
 */
public class GroupHasUsersRoleDTO {

    private static final String[] GROUP_ROLE_OPTIONS = {"Caregiver", "Client"};

    private String userRole;

    private Boolean isAdmin;

    public static String[] getGroupRoleOptions() {
        return GROUP_ROLE_OPTIONS;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
