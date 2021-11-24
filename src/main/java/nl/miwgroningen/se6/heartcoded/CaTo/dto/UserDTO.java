package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * DTO object of user for the base information about user.
 */

public class UserDTO {

    private Integer userId;
    private String name;
    private String email;
    private byte[] profilePicture;
    private boolean customProfilePicture;

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

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isCustomProfilePicture() {
        return customProfilePicture;
    }

    public void setCustomProfilePicture(boolean customProfilePicture) {
        this.customProfilePicture = customProfilePicture;
    }
}
