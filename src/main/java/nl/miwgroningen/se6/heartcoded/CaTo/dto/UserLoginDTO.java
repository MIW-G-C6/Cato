package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**

 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * DTO version of model User with password and email.
 */

public class UserLoginDTO {

    private String password;
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
