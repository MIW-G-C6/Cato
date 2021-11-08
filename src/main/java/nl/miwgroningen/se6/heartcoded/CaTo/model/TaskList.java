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
    private Group group;

    public TaskList(Group group) {
        this.group = group;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
