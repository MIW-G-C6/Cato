package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * A group which contains multiple regisered users with at least one client.
 */

@Entity
public class Group {

    @Id
    @GeneratedValue
    private Integer groupId;

    private String groupName;

    @OneToMany(mappedBy = "group")
    private List<GroupHasUsers> groupHasUsersList;

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
}
