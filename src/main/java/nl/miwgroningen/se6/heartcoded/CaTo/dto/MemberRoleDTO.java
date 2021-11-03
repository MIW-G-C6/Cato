package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 * hier komt wat het programma doet
 */
public class MemberRoleDTO {

    private String[] groupRoleOptions;

    private String userRole;

    private Boolean isAdmin;

    public String[] getGroupRoleOptions() {
        return groupRoleOptions;
    }

    public void setGroupRoleOptions(String[] groupRoleOptions) {
        this.groupRoleOptions = groupRoleOptions;
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
