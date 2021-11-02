package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import java.util.List;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 * <p>
 * Dit is wat het programma doet
 */

public class UserWithPasswordDTO {

    private Integer userId;

    private String name;

    private String password;

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

    public List<GroupHasUsersDTO> getGroupHasUsersDTOList() {
        return groupHasUsersDTOList;
    }

    public void setGroupHasUsersDTOList(List<GroupHasUsersDTO> groupHasUsersDTOList) {
        this.groupHasUsersDTOList = groupHasUsersDTOList;
    }
}
