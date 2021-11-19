package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 * hier komt wat het programma doet
 */
@Entity
public class TaskLog {

    private static final String[] PRIORITY_OPTIONS = {"Low", "Medium", "High"};

//    private static final String[] ACTIONS = {"CREATED", "UPDATED", "DELETED"};

    public enum Actions {
        CREATED, UPDATED, DELETED
    }

//    public enum Priorities {
//        LOW, MEDIUM, HIGH
//    }

    @Id
    @GeneratedValue
    private Integer taskOperationsLogId;

    private LocalDateTime dateTime;

    private Integer taskId;
//    Wat gebeurt er als je een Object verwijdert? Je history moet intact blijven.
//    misschien daarom voor een String kiezen?
//    private Task task;

//    zijn userId's wel nodig?
    private Integer userId;
//    private User user;

    private String userName;

    private Actions action;
//    Kun je hier beter voor een Enum kiezen of static final String array?
//    private String action;

    private String description;

//    private Priorities priority;
    private String priority;

    private Integer assignedUserid;

    private String assignedUser;
//    private User assignedUser;


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

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
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
