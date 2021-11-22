package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskLog;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskLogActions;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskLogRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 *
 * Gets data from the taskLog and user repositories and gives it to the controllers.
 */

@Component
public class TaskLogService {

    private final TaskLogRepository taskLogRepository;
    private final UserRepository userRepository;
    private final TaskLogEntryService taskLogEntryService;

    public TaskLogService(TaskLogRepository taskLogRepository, UserRepository userRepository,
                          TaskLogEntryService taskLogEntryService) {
        this.taskLogRepository = taskLogRepository;
        this.userRepository = userRepository;
        this.taskLogEntryService = taskLogEntryService;
    }

    @Transactional
    public void saveTaskLog(Task task, Integer userId, TaskLogActions action) {
        TaskLog result = new TaskLog();

        result.setDateTime(LocalDateTime.now());
        result.setTaskId(task.getTaskId());
        result.setUserId(userId);
        result.setUserName(userRepository.getById(userId).getName());
        result.setAction(action);

        taskLogRepository.save(result);
    }

    @Transactional
    public void saveTaskLogUpdate(Task task, Integer userId, Task oldTask) {
        TaskLog result = new TaskLog();

        result.setDateTime(LocalDateTime.now());
        result.setTaskId(task.getTaskId());
        result.setUserId(userId);
        result.setUserName(userRepository.getById(userId).getName());
        result.setAction(TaskLogActions.UPDATED);

        taskLogRepository.save(result);

        taskLogEntryService.saveTaskLogEntry(task, result.getTaskLogId(), oldTask);
    }

    public List<TaskLog> getAllByTaskId(Integer taskId) {
        return taskLogRepository.getAllByTaskId(taskId);
    }
}
