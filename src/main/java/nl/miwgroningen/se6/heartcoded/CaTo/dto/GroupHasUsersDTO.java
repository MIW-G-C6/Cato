package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * DTO version of model GroupHasUsers with userId, username and role.
 */

public class GroupHasUsersDTO {

    private Integer userId;

    private String userName;

    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
