package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 *
 * History model of task.
 */

@Entity
public class TaskLog {

    private static final String[] PRIORITY_OPTIONS = {"Low", "Medium", "High"};

    @Id
    @GeneratedValue
    private Integer taskOperationsLogId;

    private LocalDateTime dateTime;

    private Integer taskId;

    private Integer userId;

    private String userName;

    private TaskLogActions taskLogActions;

    private String description;

    private String priority;

    private Integer assignedUserid;

    private String assignedUser;

    public TaskLog() {
    }

    public static String[] getPriorityOptions() {
        return PRIORITY_OPTIONS;
    }

    public Integer getTaskOperationsLogId() {
        return taskOperationsLogId;
    }

    public void setTaskOperationsLogId(Integer taskOperationsLogId) {
        this.taskOperationsLogId = taskOperationsLogId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

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

    public TaskLogActions getTaskLogActions() {
        return taskLogActions;
    }

    public void setTaskLogActions(TaskLogActions taskLogActions) {
        this.taskLogActions = taskLogActions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getAssignedUserid() {
        return assignedUserid;
    }

    public void setAssignedUserid(Integer assignedUserid) {
        this.assignedUserid = assignedUserid;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }
}
