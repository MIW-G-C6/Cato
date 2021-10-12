package nl.miwgroningen.se6.heartcoded.CaTo.model;

import nl.miwgroningen.se6.heartcoded.CaTo.service.GroupService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 * <p>
 * Dit is wat het programma doet
 */

@Entity
public class GroupHasUsers {

    @Id
    @GeneratedValue
    private Integer groupHasUsersId;

    private String groupName;

//    private User user;

    private String role;

    public GroupHasUsers(Integer groupHasUsersId, String groupName, String role) {
        this.groupHasUsersId = groupHasUsersId;
        this.groupName = groupName;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
