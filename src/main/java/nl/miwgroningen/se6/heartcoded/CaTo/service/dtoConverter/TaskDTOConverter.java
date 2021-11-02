package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import org.springframework.stereotype.Service;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Converts Task models to DTO's and vice versa
 */

@Service
public class TaskDTOConverter {

    private TaskListDTOConverter taskListDTOConverter;

    public TaskDTOConverter(TaskListDTOConverter taskListDTOConverter) {
        this.taskListDTOConverter = taskListDTOConverter;
    }

    public TaskDTO toDTO(Task task) {
        TaskDTO result = new TaskDTO();
        result.setTaskId(task.getTaskId());
        result.setDescription(task.getDescription());
        result.setTaskList(taskListDTOConverter.toDTO(task.getTaskList()));
        result.setPriority(task.getPriority());

        return result;
    }

    public Task toModel(TaskDTO taskDTO) {
        Task result = new Task();
        result.setTaskId(taskDTO.getTaskId());
        result.setDescription(taskDTO.getDescription());
        result.setTaskList(taskListDTOConverter.toModel(taskDTO.getTaskList()));
        result.setPriority(taskDTO.getPriority());

        return result;
    }
}
