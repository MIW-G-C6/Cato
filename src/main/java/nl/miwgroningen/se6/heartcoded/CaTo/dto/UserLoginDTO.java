package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import javax.persistence.Column;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * DTO of needed User attributes to login
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
