package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Converts Task models to DTO's and vice versa
 */

@Service
public class TaskDTOConverter {

    private TaskListDTOConverter taskListDTOConverter;
    private UserDTOConverter userDTOConverter;

    public TaskDTOConverter(TaskListDTOConverter taskListDTOConverter, UserDTOConverter userDTOConverter) {
        this.taskListDTOConverter = taskListDTOConverter;
        this.userDTOConverter = userDTOConverter;
    }

    public TaskDTO toDTO(Task task) {
        TaskDTO result = new TaskDTO();
        result.setTaskId(task.getTaskId());
        result.setDescription(task.getDescription());
        result.setTaskList(taskListToDTO(task.getTaskList()));
        result.setPriority(task.getPriority());

        return result;
    }

    public Task toModel(TaskDTO taskDTO) {
        Task result = new Task();
        result.setTaskId(taskDTO.getTaskId());
        result.setDescription(taskDTO.getDescription());
        result.setTaskList(taskListToModel(taskDTO.getTaskList()));
        result.setPriority(taskDTO.getPriority());

        return result;
    }

    public TaskListDTO taskListToDTO(TaskList taskList) {
        TaskListDTO result = new TaskListDTO();
        result.setTaskListId(taskList.getTaskListId());

        List<Task> listOfTasks = taskList.getTaskList();
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task listOfTask : listOfTasks) {
            taskDTOList.add(toDTO(listOfTask));
        }
        result.setTaskList(taskDTOList);
        result.setClient(userDTOConverter.toDTO(taskList.getClient()));

        return result;
    }

    public TaskList taskListToModel(TaskListDTO taskListDTO) {
        TaskList result = new TaskList();
        result.setTaskListId(taskListDTO.getTaskListId());

        List<TaskDTO> taskDTOList = taskListDTO.getTaskList();
        List<Task> taskList = new ArrayList<>();
        for (TaskDTO taskDTO : taskDTOList) {
            taskList.add(toModel(taskDTO));
        }
        result.setTaskList(taskList);
        result.setClient(userDTOConverter.toModel(taskListDTO.getClient()));

        return result;
    }
}
