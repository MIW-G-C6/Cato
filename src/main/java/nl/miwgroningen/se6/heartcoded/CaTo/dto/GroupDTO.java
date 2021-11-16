package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 * DTO version of model Group
 */
public class GroupDTO {

    private Integer groupId;

    private String groupName;

    private Boolean currentUserIsGroupAdmin;

    public GroupDTO(String groupName) {
        this.groupName = groupName;
    }

    public GroupDTO() {
    }

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

    public Boolean getCurrentUserIsGroupAdmin() {
        return currentUserIsGroupAdmin;
    }

    public void setCurrentUserIsGroupAdmin(Boolean currentUserIsGroupAdmin) {
        this.currentUserIsGroupAdmin = currentUserIsGroupAdmin;
    }
}
