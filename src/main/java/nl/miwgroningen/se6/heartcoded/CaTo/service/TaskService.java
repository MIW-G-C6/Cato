package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.*;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.TaskMapper;
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

    public Integer getTaskListIdByTaskId(Integer taskId) {
        return taskRepository.getById(taskId).getTaskList().getTaskListId();
    }

    public List<TaskDTO> getAllTasksByGroupId(Integer groupId) {
        List<Task> listOfTasks = new ArrayList<>(taskListRepository.getByGroupGroupId(groupId).getTaskList());
        return taskMapper.toDTO(listOfTasks);
    }
}
