package nl.miwgroningen.se6.heartcoded.CaTo.model;

import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Tasks that are added to a task list.
 */

@Entity
public class Task {

    private static final String[] PRIORITY_OPTIONS = {"Low", "Medium", "High"};

    @Id
    @GeneratedValue
    private Integer taskId;

    private String description;

    @ManyToOne
    private TaskList taskList;

    private String priority;

    @Basic
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime startTime;

    @Basic
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime endTime;

    @OneToOne
    private User assignedUser;

    public Task(Integer taskId, String description, TaskList taskList, String priority, User assignedUser) {
        this.taskId = taskId;
        this.description = description;
        this.taskList = taskList;
        this.priority = priority;
        this.assignedUser = assignedUser;
    }

    public Task() {
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String[] getPriorityOptions() {
        return PRIORITY_OPTIONS;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User reservedByUser) {
        this.assignedUser = reservedByUser;
    }
}
