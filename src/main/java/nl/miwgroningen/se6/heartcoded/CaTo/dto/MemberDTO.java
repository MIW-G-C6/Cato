package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * DTO version of model Member with userId, username and role.
 */

public class MemberDTO {

    private String[] circleRoleOptions;

    private Integer userId;

    private String userName;

    private Integer circleId;

    private String role;

    private boolean isAdmin;

    public MemberDTO(Integer userId, String userName, Integer circleId, String role, boolean isAdmin) {
        this.userId = userId;
        this.userName = userName;
        this.circleId = circleId;
        this.role = role;
        this.isAdmin = isAdmin;
    }

    public MemberDTO() {
        this.circleRoleOptions = Member.getCircleRoleOptions();
    }

    public String[] getCircleRoleOptions() {
        return circleRoleOptions;
    }

    public void setCircleRoleOptions(String[] circleRoleOptions) {
        this.circleRoleOptions = circleRoleOptions;
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

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
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
