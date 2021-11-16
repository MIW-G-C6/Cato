package nl.miwgroningen.se6.heartcoded.CaTo.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        result.setCircleId(taskList.getCircle().getCircleId());

        return result;
    }

    public Optional<TaskListDTO> toDTO(Optional<TaskList> taskList) {
        Optional<TaskListDTO> taskListDTO = Optional.empty();

        if (taskList.isPresent()) {
            taskListDTO = Optional.of(toDTO(taskList.get()));
        }
        return taskListDTO;
    }

    public List<TaskListDTO> toDTO(List<TaskList> taskLists) {
        List<TaskListDTO> taskListDTOS = new ArrayList<>();
        for (TaskList taskList : taskLists) {
            taskListDTOS.add(toDTO(taskList));
        }
        return taskListDTOS;
    }

    public TaskList toTaskList(TaskListDTO taskListDTO) {
        TaskList result = new TaskList();
        result.setTaskListId(taskListDTO.getTaskListId());
        return result;
    }
}
