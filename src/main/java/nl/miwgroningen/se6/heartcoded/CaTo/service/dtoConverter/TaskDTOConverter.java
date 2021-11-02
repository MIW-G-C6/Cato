package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.persistence.Converter;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 */

@Service
public class TaskDTOConverter {

    public TaskDTO toDTO(Task task) {
        TaskDTO result = new TaskDTO();
        result.setTaskId(task.getTaskId());
        result.setDescription(task.getDescription());
        //TODO result.setTaskList(taskListDTOConverter.toDTO(task.getTaskList()));
        result.setPriority(task.getPriority());

        return result;
    }

    public Task toModel(TaskDTO taskDTO) {
        Task result = new Task();
        result.setTaskId(taskDTO.getTaskId());
        result.setDescription(taskDTO.getDescription());
        //TODO result.setTaskList(taskListDTOConverter.toModel(taskDTO.getTaskList()));
        result.setPriority(taskDTO.getPriority());

        return result;
    }
}