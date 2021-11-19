package nl.miwgroningen.se6.heartcoded.CaTo.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskNotificationDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import org.springframework.stereotype.Component;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Maps the task model to a taskNotification DTO and back
 */
@Component
public class TaskNotificationMapper {

    public TaskNotificationDTO toDTO(Task task) {
        TaskNotificationDTO taskNotificationDTO = new TaskNotificationDTO();
        taskNotificationDTO.setTaskId(task.getTaskId());
        taskNotificationDTO.setDescription(task.getDescription());
        taskNotificationDTO.setTaskListId(task.getTaskList().getTaskListId());
        taskNotificationDTO.setPriority(task.getPriority());
        taskNotificationDTO.setAssigned(task.getAssignedUser() != null);
        taskNotificationDTO.setStartTime(task.getStartTime());
        taskNotificationDTO.setEndTime(task.getEndTime());
        return taskNotificationDTO;
    }
}
