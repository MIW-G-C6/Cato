package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * TaskDTO containing all information for tasks.
 */

public class TaskDTO {

    private String[] priorityOptions;
    private Integer taskId;
    private String priority;
    private String description;
    private Integer taskListId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    //nodig om op te slaan wie de taak reserveert (voor nu alleen currentUser --> later ook anderen kunnen assignen)
    private Integer assignedUserId;
    //nodig om te laten zien in de view (default lege string? --> view laat knopje zien: assign)
    private String assignedUserName;

    public TaskDTO(String[] priorityOptions, String priority, String description, Integer taskListId) {
        this.priorityOptions = priorityOptions;
        this.priority = priority;
        this.description = description;
        this.taskListId = taskListId;
    }

    public TaskDTO(String priority, String description, Integer taskListId) {
        this.priority = priority;
        this.description = description;
        this.taskListId = taskListId;
    }

    public TaskDTO() {
        Task taskForPriority = new Task();
        this.priorityOptions = taskForPriority.getPriorityOptions();
    }

    public String[] getPriorityOptions() {
        return priorityOptions;
    }

    public void setPriorityOptions(String[] priorityOptions) {
        this.priorityOptions = priorityOptions;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Integer taskListId) {
        this.taskListId = taskListId;
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

    public Integer getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Integer assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getAssignedUserName() {
        return assignedUserName;
    }

    public void setAssignedUserName(String assignedUserName) {
        this.assignedUserName = assignedUserName;
    }
}
