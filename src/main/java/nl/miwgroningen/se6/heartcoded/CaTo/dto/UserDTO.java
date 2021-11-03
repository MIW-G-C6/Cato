package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import javax.persistence.Column;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * DTO object of user for the base information about user
 */

public class UserDTO {

    private Integer userId;
    private String name;
    private String email;

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
}
