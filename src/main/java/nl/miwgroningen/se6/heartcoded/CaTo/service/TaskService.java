package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.*;
import nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers.TaskMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the task repository and gives it to the controllers
 */

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskListRepository taskListRepository,
                       UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskDTO> findAllTasks() {
        return taskMapper.toDTO(taskRepository.findAll());
    }

    public Optional<TaskDTO> findById(Integer taskId) {
        return taskMapper.toDTO(taskRepository.findById(taskId));
    }

    public void deleteById(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    public void save(TaskDTO taskDTO, Integer taskListId) {
        Task task = taskMapper.toTask(taskDTO);
        task.setTaskList(taskListRepository.getById(taskListId));
        taskRepository.save(task);
    }

    public void assignUser(Integer taskId, Integer userId) {
        Task task = taskRepository.getById(taskId);
        task.setAssignedUser(userRepository.getById(userId));
        taskRepository.save(task);
    }

    public void unassignUser(Integer taskId) {
        Task task = taskRepository.getById(taskId);
        task.setAssignedUser(null);
        taskRepository.save(task);
    }

    public Integer getTaskListIdByTaskId(Integer taskId) {
        return taskRepository.getById(taskId).getTaskList().getTaskListId();
    }

    public List<TaskDTO> getAllTasksByCircleId(Integer circleId) {
        List<Task> listOfTasks = new ArrayList<>(taskListRepository.getByCircleCircleId(circleId).getTaskList());
        return taskMapper.toDTO(listOfTasks);
    }
}
