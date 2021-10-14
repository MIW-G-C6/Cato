package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
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
public class TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    public List<TaskList> findAll() {
        return taskListRepository.findAll();
    }

    public List<TaskList> findAllByGroupId(Integer groupId) {
        List<TaskList> allTaskLists = new ArrayList<>();
        List<Integer> allTaskListIds = taskListRepository.findAllTaskListIdsByGroupId(groupId);
        for (Integer taskListId : allTaskListIds) {
            allTaskLists.add(taskListRepository.getById(taskListId));
        }
        return allTaskLists;
    }

    public Optional<TaskList> findById(Integer taskListId) {
        return taskListRepository.findById(taskListId);
    }

    public TaskList getById(Integer taskListId) {
        return taskListRepository.getById(taskListId);
    }

    public void save(TaskList taskList) {
        taskListRepository.save(taskList);
    }

    public TaskList findByUser(User user) {
        return taskListRepository.findByClient(user);
    }
}

