package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * DTO version of the taskList model.
 */

public class TaskListDTO {

    private Integer taskListId;

    private List<TaskDTO> taskList;

    private UserDTO client;

    public TaskListDTO(UserDTO client) {
        this.taskList = new ArrayList<>();
        this.client = client;
    }

    public TaskListDTO() {
    }

    public Integer getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Integer taskListId) {
        this.taskListId = taskListId;
    }

    public List<TaskDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskDTO> taskList) {
        this.taskList = taskList;
    }

    public UserDTO getClient() {
        return client;
    }

    public void setClient(UserDTO client) {
        this.client = client;
    }
}
