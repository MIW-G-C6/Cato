package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import org.springframework.stereotype.Component;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Maps the taskList model to a tasklist DTO and back.
 */

@Component
public class TaskListMapper {

    public TaskListDTO toDTO(TaskList taskList) {
        TaskListDTO result = new TaskListDTO();

        result.setTaskListId(taskList.getTaskListId());
        result.setUserId(taskList.getClient().getUserId());
        result.setUserName(taskList.getClient().getUsername());

        return result;
    }

    public TaskList toTaskList(TaskListDTO taskListDTO) {
        TaskList result = new TaskList();
        result.setTaskListId(taskListDTO.getTaskListId());
        return result;
    }
}
