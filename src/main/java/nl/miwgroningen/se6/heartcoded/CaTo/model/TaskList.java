package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * List of tasks
 */
@Entity
public class TaskList {
    @Id
    @GeneratedValue
    private Integer taskListId;

    @OneToMany(mappedBy = "taskList")
    private List<Task> taskList;

    @OneToOne
    private User client;

    public TaskList(User client) {
        this.client = client;
        this.taskList = new ArrayList<>();
    }

    public TaskList() {
    }

    public Integer getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Integer taskListId) {
        this.taskListId = taskListId;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
