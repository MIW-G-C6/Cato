package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * TaskListDTO containing all information for taskList
 */

public class TaskListDTO {

    private Integer userId;

    private String userName;

    private Integer taskListId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Integer taskListId) {
        this.taskListId = taskListId;
    }
}
