package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskLog;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskLogRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 *
 * Gets data from the taskLog and user repositories and gives it to the controllers.
 */

@Component
public class TaskLogService {

    private final TaskLogRepository taskLogRepository;
    private final UserRepository userRepository;

    public TaskLogService(TaskLogRepository taskLogRepository,
                          UserRepository userRepository) {
        this.taskLogRepository = taskLogRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveTaskLog(Task task, Integer userId, TaskLog.Actions action) {
        TaskLog result = new TaskLog();

        result.setDateTime(LocalDateTime.now());
        result.setTaskId(task.getTaskId());
        result.setUserId(userId);
        result.setUserName(userRepository.getById(userId).getName());
        result.setAction(action);
        result.setDescription(task.getDescription());
        result.setPriority(task.getPriority());

        if (task.getAssignedUser() != null) {
            result.setAssignedUserid(task.getAssignedUser().getUserId());
            result.setAssignedUser(task.getAssignedUser().getName());
        }

        taskLogRepository.save(result);
    }
}
