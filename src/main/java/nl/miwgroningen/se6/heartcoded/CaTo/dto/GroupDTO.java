package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import java.util.List;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 * DTO version of model Group
 */
public class GroupDTO {

    private Integer groupId;

    private String groupName;

    private List<GroupHasUsersDTO> groupHasUsersDTOList;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<GroupHasUsersDTO> getGroupHasUsersDTOList() {
        return groupHasUsersDTOList;
    }

    public void setGroupHasUsersDTOList(List<GroupHasUsersDTO> groupHasUsersDTOList) {
        this.groupHasUsersDTOList = groupHasUsersDTOList;
    }
}
