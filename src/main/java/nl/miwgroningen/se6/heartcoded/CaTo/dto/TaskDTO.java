package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import java.util.List;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * TaskDTO containing all information for tasks
 */
public class TaskDTO {
    private List<String> priorityOptions;

    private Integer taskId;

    private String priority;

    private String description;

    public List<String> getPriorityOptions() {
        return priorityOptions;
    }

    public void setPriorityOptions(List<String> priorityOptions) {
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
}
