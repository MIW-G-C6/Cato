package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * DTO version of model GroupHasUsers with userId, username and role.
 */

public class MemberDTO {

    private String[] groupRoleOptions;

    private Integer userId;

    private String userName;

    private Integer groupId;

    private String role;

    private boolean isAdmin;

    public MemberDTO(Integer userId, String userName, Integer groupId, String role, boolean isAdmin) {
        this.userId = userId;
        this.userName = userName;
        this.groupId = groupId;
        this.role = role;
        this.isAdmin = isAdmin;
    }

    public MemberDTO() {
        this.groupRoleOptions = Member.getGroupRoleOptions();
    }

    public String[] getGroupRoleOptions() {
        return groupRoleOptions;
    }

    public void setGroupRoleOptions(String[] groupRoleOptions) {
        this.groupRoleOptions = groupRoleOptions;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
