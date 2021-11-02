package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.TaskDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the task repository and gives it to the controllers
 */

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskDTOConverter taskDTOConverter;

    public TaskService(TaskRepository taskRepository, TaskDTOConverter taskDTOConverter) {
        this.taskRepository = taskRepository;
        this.taskDTOConverter = taskDTOConverter;
    }

    public Optional<TaskDTO> findById(Integer taskId) {
        Optional<TaskDTO> taskDTO = Optional.empty();

        Optional<Task> task = taskRepository.findById(taskId);
        if (!task.isEmpty()) {
            taskDTO = Optional.of(taskDTOConverter.toDTO(task.get()));
        }

        return taskDTO;
    }

    public TaskDTO getById(Integer taskId) {
        return taskDTOConverter.toDTO(taskRepository.getById(taskId));
    }

    public void deleteById(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    public void save(TaskDTO task) {
        taskRepository.save(taskDTOConverter.toModel(task));
    }

    public Integer getTaskListIdByTaskId(Integer taskId) {
        return taskDTOConverter.toDTO(taskRepository.getById(taskId)).getTaskList().getTaskListId();
    }
}
