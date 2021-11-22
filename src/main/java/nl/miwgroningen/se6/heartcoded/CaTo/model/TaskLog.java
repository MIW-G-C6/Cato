package nl.miwgroningen.se6.heartcoded.CaTo.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 *
 * Container for changes made to model Task.
 */

@Entity
public class TaskLog {

    @Id
    @GeneratedValue
    private Integer taskLogId;

    @Basic
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dateTime;

    private Integer taskId;

    private Integer userId;

    private String userName;

    private TaskLogActions action;

    public TaskLog() {
    }

    public Integer getTaskLogId() {
        return taskLogId;
    }

    public void setTaskLogId(Integer taskLogId) {
        this.taskLogId = taskLogId;
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

    public TaskLogActions getAction() {
        return action;
    }

    public void setAction(TaskLogActions action) {
        this.action = action;
    }
}
