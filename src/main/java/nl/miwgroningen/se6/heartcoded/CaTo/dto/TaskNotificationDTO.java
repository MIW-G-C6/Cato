package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * TaskDTO containing all information necessary for the notifications menu.
 */

public class TaskNotificationDTO {

    private Integer taskId;

    private String description;

    private Integer circleId;

    private String circleName;

    private Integer taskListId;

    private String priority;

    private boolean isAssigned;

    private LocalDate startTime;

    private LocalDate endTime;

    public TaskNotificationDTO() {
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

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Integer getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Integer taskListId) {
        this.taskListId = taskListId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }
}
