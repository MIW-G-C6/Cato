package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * UserDTO containing all attributes to create a new User.
 */

public class UserRegistrationDTO {

    private Integer userId;
    private String name;
    private String password;
    private String passwordCheck;
    private String email;

    public UserRegistrationDTO(String name, String password, String passwordCheck, String email) {
        this.name = name;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.email = email;
    }

    public UserRegistrationDTO() {
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
