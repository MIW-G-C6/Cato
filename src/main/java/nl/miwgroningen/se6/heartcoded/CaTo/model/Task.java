package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Tasks that are added to a task list
 */

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Integer taskId;

    private String description;

    @ManyToOne
    private TaskList taskListId;

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

    public TaskList getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(TaskList taskListId) {
        this.taskListId = taskListId;
    }
}
