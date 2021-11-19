package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskLog;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskLogActions;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.*;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.TaskMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Gets data from the task repository and gives it to the controllers.
 */

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;
    private final UserRepository userRepository;
    private final TaskLogService taskLogService;

    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskListRepository taskListRepository,
                       UserRepository userRepository, TaskLogService taskLogService, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
        this.userRepository = userRepository;
        this.taskLogService = taskLogService;
        this.taskMapper = taskMapper;
    }

    public List<TaskDTO> findAllTasks() {
        return taskMapper.toDTO(taskRepository.findAll());
    }

    public Long totalNumberOfTasks() {
        return taskRepository.count();
    }

    public Integer totalNumberOfReservedTasks() {
        return taskRepository.countAllByAssignedUserNotNull();
    }

    public Optional<TaskDTO> findById(Integer taskId) {
        return taskMapper.toDTO(taskRepository.findById(taskId));
    }

    public void deleteById(Integer taskId, Integer userId) {
        Task task = taskRepository.getById(taskId);

        taskRepository.deleteById(taskId);

        taskLogService.saveTaskLog(task, userId, TaskLogActions.DELETED);
    }

    public void save(TaskDTO taskDTO, Integer taskListId, Integer userId) {
        Task task = taskMapper.toTask(taskDTO);
        task.setTaskList(taskListRepository.getById(taskListId));

        TaskLogActions taskLogActions;

        if (task.getTaskId() == null) {
            taskLogActions = TaskLogActions.CREATED;
        } else {
            task.setAssignedUser(taskRepository.getById(task.getTaskId()).getAssignedUser());
            taskLogActions = TaskLogActions.UPDATED_PROPERTIES;
        }
        taskRepository.save(task);

        taskLogService.saveTaskLog(task, userId, taskLogActions);
    }

    public void assignUser(Integer taskId, Integer userId) {
        Task task = taskRepository.getById(taskId);
        task.setAssignedUser(userRepository.getById(userId));

        taskRepository.save(task);

        taskLogService.saveTaskLog(task, userId, TaskLogActions.UPDATED_ASSIGNED_USER);
    }

    public void unassignUser(Integer taskId, Integer userId) {
        Task task = taskRepository.getById(taskId);
        task.setAssignedUser(null);

        taskRepository.save(task);

        taskLogService.saveTaskLog(task, userId, TaskLogActions.UPDATED_ASSIGNED_USER);
    }

    public Integer getTaskListIdByTaskId(Integer taskId) {
        return taskRepository.getById(taskId).getTaskList().getTaskListId();
    }

    public List<TaskDTO> getAllTasksByCircleId(Integer circleId) {
        List<Task> listOfTasks = new ArrayList<>(taskListRepository.getByCircleCircleId(circleId).getTaskList());

        return taskMapper.toDTO(listOfTasks);
    }
}
