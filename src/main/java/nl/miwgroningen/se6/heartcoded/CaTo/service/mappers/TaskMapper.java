package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        taskDTO.setTaskListId(task.getTaskList().getTaskListId());

        return taskDTO;
    }

    public Optional<TaskDTO> toDTO(Optional<Task> task) {
        Optional<TaskDTO> taskDTO = Optional.empty();

        if (task.isPresent()) {
            taskDTO = Optional.of(toDTO(task.get()));
        }
        return taskDTO;
    }

    public List<TaskDTO> toDTO(List<Task> listOfTasks) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task : listOfTasks) {
            taskDTOList.add(toDTO(task));
        }
        return taskDTOList;
    }

    public Task toTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTaskId(taskDTO.getTaskId());
        task.setPriority(taskDTO.getPriority());
        task.setDescription(taskDTO.getDescription());
        return task;
    }
}
