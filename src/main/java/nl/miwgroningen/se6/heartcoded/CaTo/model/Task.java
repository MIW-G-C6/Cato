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

    private static final String[] PRIORITY_OPTIONS = {"Low", "Medium", "High"};

    @Id
    @GeneratedValue
    private Integer taskId;

    private String description;

    @ManyToOne
    private TaskList taskList;

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

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskListId) {
        this.taskList = taskListId;
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
