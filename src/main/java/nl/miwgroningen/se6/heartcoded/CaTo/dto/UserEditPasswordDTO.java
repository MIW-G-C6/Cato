package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 *
 * DTO object of user that is used for editing passwords.
 */

public class UserEditPasswordDTO {

    private Integer userId;
    private String name;
    //TODO remove email
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
