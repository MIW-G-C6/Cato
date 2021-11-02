package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import java.util.List;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * This is the DTO model from User without the password
 */

public class UserDTO {

    private Integer userId;

    private String name;

    private String email;

    private List<GroupHasUsersDTO> groupHasUsersDTOList;

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

    public List<GroupHasUsersDTO> getGroupHasUsersDTOList() {
        return groupHasUsersDTOList;
    }

    public void setGroupHasUsersDTOList(List<GroupHasUsersDTO> groupHasUsersDTOList) {
        this.groupHasUsersDTOList = groupHasUsersDTOList;
    }
}
