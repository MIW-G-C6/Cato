package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskNotificationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.TaskNotificationMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskLog;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.*;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.TaskMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

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
    private final MemberRepository memberRepository;

    private final TaskMapper taskMapper;
    private final TaskNotificationMapper taskNotificationMapper;

    public TaskService(TaskRepository taskRepository, TaskListRepository taskListRepository,
                       UserRepository userRepository, MemberRepository memberRepository, TaskMapper taskMapper,
                       TaskNotificationMapper taskNotificationMapper, TaskLogService taskLogService) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.taskLogService = taskLogService;
        this.taskMapper = taskMapper;
        this.taskNotificationMapper = taskNotificationMapper;
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

        taskLogService.saveTaskLog(task, userId, TaskLog.Actions.DELETED);
    }

    public void save(TaskDTO taskDTO, Integer taskListId, Integer userId) {
        Task task = taskMapper.toTask(taskDTO);
        task.setTaskList(taskListRepository.getById(taskListId));

        TaskLog.Actions action;

        if (task.getTaskId() == null) {
            action = TaskLog.Actions.CREATED;
        } else {
            task.setAssignedUser(taskRepository.getById(task.getTaskId()).getAssignedUser());
            action = TaskLog.Actions.UPDATED;
        }
        taskRepository.save(task);

        taskLogService.saveTaskLog(task, userId, action);
    }

    public void assignUser(Integer taskId, Integer userId) {
        Task task = taskRepository.getById(taskId);
        task.setAssignedUser(userRepository.getById(userId));

        taskRepository.save(task);

        taskLogService.saveTaskLog(task, userId, TaskLog.Actions.UPDATED);
    }

    public void unassignUser(Integer taskId, Integer userId) {
        Task task = taskRepository.getById(taskId);
        task.setAssignedUser(null);

        taskRepository.save(task);

        taskLogService.saveTaskLog(task, userId, TaskLog.Actions.UPDATED);
    }

    public Integer getTaskListIdByTaskId(Integer taskId) {
        return taskRepository.getById(taskId).getTaskList().getTaskListId();
    }

    public List<TaskDTO> getAllTasksByCircleId(Integer circleId) {
        List<Task> listOfTasks = taskListRepository.getByCircleCircleId(circleId).getTaskList();
        return taskMapper.toDTO(listOfTasks);
    }

    public List<TaskNotificationDTO> getAllNotificationTasksByUserId(Integer userId) {
        List<Member> allMember = memberRepository.getAllByUser(userRepository.getById(userId));
        List<TaskNotificationDTO> allTasks = new ArrayList<>();

        for (Member member : allMember) {
            for (Task task :  taskListRepository.getByCircleCircleId(member.getCircle().getCircleId()).getTaskList()) {
                if (isHighPriorityAndNoAssignedUserAndHasEndTime(userId, task)) {
                    TaskNotificationDTO taskNotificationDTO = taskNotificationMapper.toDTO(task);
                    taskNotificationDTO.setCircleId(member.getCircle().getCircleId());
                    taskNotificationDTO.setCircleName(member.getCircle().getCircleName());
                    allTasks.add(taskNotificationDTO);
                }
            }
        }

        allTasks.sort(Comparator.comparing(TaskNotificationDTO :: getEndTime)); //TODO set max limit of tasks to show

        return allTasks;
    }

    private boolean isHighPriorityAndNoAssignedUserAndHasEndTime(Integer userId, Task task) {
        return task.getPriority().equals("High") &&
                (task.getAssignedUser() == null || Objects.equals(task.getAssignedUser().getUserId(), userId)) &&
                task.getEndTime() != null;
    }
}
