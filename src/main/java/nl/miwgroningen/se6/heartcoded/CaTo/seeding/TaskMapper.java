package nl.miwgroningen.se6.heartcoded.CaTo.seeding;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import org.springframework.stereotype.Component;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Maps Task models to DTOs and back
 */
@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setPriorityOptions(task.getPriorityOptions());
        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setPriority(task.getPriority());

        return taskDTO;
    }

    public Task toTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTaskId(taskDTO.getTaskId());
        task.setPriority(taskDTO.getPriority());
        task.setDescription(taskDTO.getDescription());
        return task;
    }
}
