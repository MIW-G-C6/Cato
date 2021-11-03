package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.TaskListMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.UserMapper;
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
    private final UserRepository userRepository;
    private final TaskListMapper taskListMapper;

    public TaskListService(TaskListRepository taskListRepository, TaskListMapper taskListMapper, UserRepository userRepository) {
        this.taskListRepository = taskListRepository;
        this.taskListMapper = taskListMapper;
        this.userRepository = userRepository;
    }

    public List<TaskListDTO> findAll() {
        return taskListMapper.toDTO(taskListRepository.findAll());
    }

    public List<TaskListDTO> findAllByGroupId(Integer groupId) {
        List<TaskListDTO> allTaskLists = new ArrayList<>();
        List<Integer> allTaskListIds = taskListRepository.findAllTaskListIdsByGroupId(groupId);
        for (Integer taskListId : allTaskListIds) {
            if (taskListId != null && taskListRepository.findById(taskListId).isPresent()) {
                allTaskLists.add(taskListMapper.toDTO(taskListRepository.getById(taskListId)));
            }
        }
        return allTaskLists;
    }

    public Optional<TaskListDTO> findById(Integer taskListId) {
        return taskListMapper.toDTO(taskListRepository.findById(taskListId));
    }

    public TaskListDTO getById(Integer taskListId) {
        return taskListMapper.toDTO(taskListRepository.getById(taskListId));
    }

    public void save(TaskListDTO taskListDTO) {
        TaskList taskList = taskListMapper.toTaskList(taskListDTO);
        taskList.setClient(userRepository.getById(taskListDTO.getUserId()));
        //TODO

        taskListRepository.save(taskList);
    }

    public TaskListDTO findByUser(UserDTO user) {
        User client = userRepository.getById(user.getUserId());
        return taskListMapper.toDTO(taskListRepository.findByClient(client));
    }
}

