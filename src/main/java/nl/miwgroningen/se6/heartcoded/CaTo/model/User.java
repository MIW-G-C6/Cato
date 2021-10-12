package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.*;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Someone who can log in to the CaTo WebApp.
 */

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer userId;

    private String name;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    public User(Integer userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public User() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
