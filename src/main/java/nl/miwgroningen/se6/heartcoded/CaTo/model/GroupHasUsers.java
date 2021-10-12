package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.*;


/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Users in a specific groups that get a specific role for that group
 */

@Entity
public class GroupHasUsers {
    @Id
    @GeneratedValue
    private Integer groupId;

    private Integer userId;

    private String userRole;

    public GroupHasUsers(Integer groupId, Integer userId, String userRole) {
        this.groupId = groupId;
        this.userId = userId;
        this.userRole = userRole;
    }

    public GroupHasUsers() {
    }

    public Integer getGroupsId() {
        return groupId;
    }

    public void setGroupsId(Integer groupsId) {
        this.groupId = groupsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
