package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Users in a specific groups that get a specific role for that group
 */

@Entity
public class GroupHasUsers {

    @Id
    @GeneratedValue
    private Integer groupHasUsersId;

    private String groupName;

    private String email;

    private String role;

    public GroupHasUsers(Integer groupHasUsersId, String groupName, String email, String role) {
        this.groupHasUsersId = groupHasUsersId;
        this.groupName = groupName;
        this.email = email;
        this.role = role;
    }

    public GroupHasUsers() {
    }

    public Integer getGroupHasUsersId() {
        return groupHasUsersId;
    }

    public void setGroupHasUsersId(Integer groupHasUsersId) {
        this.groupHasUsersId = groupHasUsersId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
