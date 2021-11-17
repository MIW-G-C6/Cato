package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * TaskListDTO containing all information for taskList.
 */

public class TaskListDTO {

    private Integer taskListId;
    private Integer circleId;

    public TaskListDTO(Integer taskListId, Integer circleId) {
        this.taskListId = taskListId;
        this.circleId = circleId;
    }

    public TaskListDTO() {
    }

    public Integer getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Integer taskListId) {
        this.taskListId = taskListId;
    }

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }
}
