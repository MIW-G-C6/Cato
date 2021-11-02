package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * DTO version of model Task
 */
public class TaskDTO {

    private static final String[] PRIORITY_OPTIONS = {"Low", "Medium", "High"};

    private Integer taskId;

    private String description;

    private TaskListDTO taskList;

    private String priority;

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

    public TaskListDTO getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskListDTO taskList) {
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
}
