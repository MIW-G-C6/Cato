package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * TaskListDTO containing all information for taskList
 */

public class TaskListDTO {

    private Integer taskListId;

    private Integer groupId;

    public TaskListDTO(Integer taskListId, Integer groupId) {
        this.taskListId = taskListId;
        this.groupId = groupId;
    }

    public TaskListDTO() {
    }

    public Integer getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Integer taskListId) {
        this.taskListId = taskListId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
