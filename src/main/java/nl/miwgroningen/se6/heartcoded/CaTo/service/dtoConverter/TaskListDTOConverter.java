package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Converts TaskList models to TaskList DTO's and back.
 */

@Service
public class TaskListDTOConverter {

    private UserDTOConverter userDTOConverter;
    private TaskDTOConverter taskDTOConverter;

    public TaskListDTOConverter(UserDTOConverter userDTOConverter, TaskDTOConverter taskDTOConverter) {
        this.userDTOConverter = userDTOConverter;
        this.taskDTOConverter = taskDTOConverter;
    }

    public TaskListDTO toDTO(TaskList taskList) {
        TaskListDTO result = new TaskListDTO();
        result.setTaskListId(taskList.getTaskListId());

        List<Task> listOfTasks = taskList.getTaskList();
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task listOfTask : listOfTasks) {
            taskDTOList.add(taskDTOConverter.toDTO(listOfTask));
        }
        result.setTaskList(taskDTOList);
        result.setClient(userDTOConverter.toDTO(taskList.getClient()));

        return result;
    }

    public TaskList toModel(TaskListDTO taskListDTO) {
        TaskList result = new TaskList();
        result.setTaskListId(taskListDTO.getTaskListId());

        List<TaskDTO> taskDTOList = taskListDTO.getTaskList();
        List<Task> taskList = new ArrayList<>();
        for (TaskDTO taskDTO : taskDTOList) {
            taskList.add(taskDTOConverter.toModel(taskDTO));
        }
        result.setTaskList(taskList);
        result.setClient(userDTOConverter.toModel(taskListDTO.getClient()));

        return result;
    }
}
