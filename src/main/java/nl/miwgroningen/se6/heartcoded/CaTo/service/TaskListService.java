package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.TaskListDTOConverter;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.UserDTOConverter;
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
    private final TaskListDTOConverter taskListDTOConverter;
    private final UserDTOConverter userDTOConverter;

    public TaskListService(TaskListRepository taskListRepository, TaskListDTOConverter taskListDTOConverter, UserDTOConverter userDTOConverter) {
        this.taskListRepository = taskListRepository;
        this.taskListDTOConverter = taskListDTOConverter;
        this.userDTOConverter = userDTOConverter;
    }

    public List<TaskListDTO> findAll() {
        List<TaskList> allTaskLists = taskListRepository.findAll();
        List<TaskListDTO> result = new ArrayList<>();
        for (TaskList taskList : allTaskLists) {
            result.add(taskListDTOConverter.toDTO(taskList));
        }
        return result;
    }

    public List<TaskListDTO> findAllByGroupId(Integer groupId) {
        List<TaskListDTO> allTaskLists = new ArrayList<>();
        List<Integer> allTaskListIds = taskListRepository.findAllTaskListIdsByGroupId(groupId);
        for (Integer taskListId : allTaskListIds) {
            if (taskListId != null && taskListRepository.findById(taskListId).isPresent()) {
                allTaskLists.add(taskListDTOConverter.toDTO(taskListRepository.getById(taskListId)));
            }
        }
        return allTaskLists;
    }

    public Optional<TaskListDTO> findById(Integer taskListId) {
        Optional<TaskListDTO> taskListDTO = Optional.empty();

        Optional<TaskList> taskList = taskListRepository.findById(taskListId);
        if(!taskList.isEmpty()) {
            taskListDTO = Optional.of(taskListDTOConverter.toDTO(taskList.get()));
        }
        return taskListDTO;
    }

    public TaskListDTO getById(Integer taskListId) {
        return taskListDTOConverter.toDTO(taskListRepository.getById(taskListId));
    }

    public void save(TaskListDTO taskList) {
        taskListRepository.save(taskListDTOConverter.toModel(taskList));
    }

    public TaskListDTO findByUser(UserDTO user) {
        return taskListDTOConverter.toDTO(taskListRepository.findByClient(userDTOConverter.toModel(user)));
    }
}

