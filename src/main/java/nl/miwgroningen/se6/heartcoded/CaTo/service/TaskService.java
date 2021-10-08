package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskRepository;
import org.springframework.stereotype.Service;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the task repository and gives it to the controllers
 */

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getById(Integer taskId) {
        return taskRepository.getById(taskId);
    }

    public void deleteById(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public Integer getTaskListIdByTaskId(Integer taskId) {
        return taskRepository.getById(taskId).getTaskList().getTaskListId();
    }
}
