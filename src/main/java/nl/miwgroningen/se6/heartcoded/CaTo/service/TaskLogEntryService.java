package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskLogEntry;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskLogEntryRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 *
 * Gets data from tasks, checks what values are changed and saves them as seperate entries.
 */

@Component
public class TaskLogEntryService {

    private final TaskRepository taskRepository;
    private final TaskLogEntryRepository taskLogEntryRepository;

    public TaskLogEntryService(TaskRepository taskRepository, TaskLogEntryRepository taskLogEntryRepository) {
        this.taskRepository = taskRepository;
        this.taskLogEntryRepository = taskLogEntryRepository;
    }

    @Transactional
    public void saveTaskLogEntry(Task task, Integer taskLogId, Task oldTask) {

        compareAssignedUser(task, taskLogId, oldTask);

        compareDescription(task, taskLogId, oldTask);

        comparePriority(task, taskLogId, oldTask);

        compareStartTime(task, taskLogId, oldTask);

        compareEndTime(task, taskLogId, oldTask);
    }

    private void compareAssignedUser(Task task, Integer taskLogId, Task oldTask) {

        if (oldTask.getAssignedUser() != task.getAssignedUser()) {
            TaskLogEntry taskLogEntry = new TaskLogEntry();
            taskLogEntry.setTaskLogId(taskLogId);
            taskLogEntry.setChangedProperty("assigned user");

            if (oldTask.getAssignedUser() == null) {
                taskLogEntry.setOldValue(null);
            } else {
                taskLogEntry.setOldId(oldTask.getAssignedUser().getUserId());
                taskLogEntry.setOldValue(oldTask.getAssignedUser().getName());
            }

            if (task.getAssignedUser() == null) {
                taskLogEntry.setNewValue(null);
            } else {
                taskLogEntry.setNewId(task.getAssignedUser().getUserId());
                taskLogEntry.setNewValue(task.getAssignedUser().getName());
            }

            taskLogEntryRepository.save(taskLogEntry);
        }
    }

    private void compareDescription(Task task, Integer taskLogId, Task oldTask) {
        if (!oldTask.getDescription().equals(task.getDescription())) {
            TaskLogEntry taskLogEntry = new TaskLogEntry();
            taskLogEntry.setTaskLogId(taskLogId);
            taskLogEntry.setChangedProperty("description");

            if (oldTask.getDescription() == null) {
                taskLogEntry.setOldValue(null);
            } else {
                taskLogEntry.setOldValue(oldTask.getDescription());
            }

            if (task.getDescription() == null) {
                taskLogEntry.setNewValue(null);
            } else {
                taskLogEntry.setNewValue(task.getDescription());
            }

            taskLogEntryRepository.save(taskLogEntry);
        }
    }

    private void comparePriority(Task task, Integer taskLogId, Task oldTask) {
        if (!oldTask.getPriority().equals(task.getPriority())) {
            TaskLogEntry taskLogEntry = new TaskLogEntry();
            taskLogEntry.setTaskLogId(taskLogId);
            taskLogEntry.setChangedProperty("priority");

            if (oldTask.getPriority() == null) {
                taskLogEntry.setOldValue(null);
            } else {
                taskLogEntry.setOldValue(oldTask.getPriority());
            }

            if (task.getPriority() == null) {
                taskLogEntry.setNewValue(null);
            } else {
                taskLogEntry.setNewValue(task.getPriority());
            }

            taskLogEntryRepository.save(taskLogEntry);
        }
    }

    private void compareStartTime(Task task, Integer taskLogId, Task oldTask) {
        if (oldTask.getStartTime() != task.getStartTime()) {
            TaskLogEntry taskLogEntry = new TaskLogEntry();
            taskLogEntry.setTaskLogId(taskLogId);
            taskLogEntry.setChangedProperty("start time");

            if (oldTask.getStartTime() == null) {
                taskLogEntry.setOldValue(null);
            } else {
                taskLogEntry.setOldValue(oldTask.getStartTime()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }

            if (task.getStartTime() == null) {
                taskLogEntry.setNewValue(null);
            } else {
                taskLogEntry.setNewValue(task.getStartTime()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }

            taskLogEntryRepository.save(taskLogEntry);
        }
    }

    private void compareEndTime(Task task, Integer taskLogId, Task oldTask) {
        if (oldTask.getEndTime() != task.getEndTime()) {
            TaskLogEntry taskLogEntry = new TaskLogEntry();
            taskLogEntry.setTaskLogId(taskLogId);
            taskLogEntry.setChangedProperty("end time");

            if (oldTask.getEndTime() == null) {
                taskLogEntry.setOldValue(null);
            } else {
                taskLogEntry.setOldValue(oldTask.getEndTime()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }

            if (task.getEndTime() == null) {
                taskLogEntry.setNewValue(null);
            } else {
                taskLogEntry.setNewValue(task.getEndTime()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }

            taskLogEntryRepository.save(taskLogEntry);
        }
    }
}
