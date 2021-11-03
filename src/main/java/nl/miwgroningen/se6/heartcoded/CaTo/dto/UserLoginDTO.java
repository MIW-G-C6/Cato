package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import javax.persistence.Column;

/**
<<<<<<< HEAD
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * DTO of needed User attributes to login
=======
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * DTO version of model User with password and email.
>>>>>>> 7980019a04a156b7932efdf522f0c8bf0ef8c5ca
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
