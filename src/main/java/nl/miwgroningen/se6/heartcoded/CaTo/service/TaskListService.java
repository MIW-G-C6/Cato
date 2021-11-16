package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.CircleDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.CircleRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.TaskListMapper;
import org.springframework.stereotype.Service;

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
    private final CircleRepository circleRepository;
    private final TaskListMapper taskListMapper;

    public TaskListService(TaskListRepository taskListRepository, CircleRepository circleRepository,
                           TaskListMapper taskListMapper) {
        this.taskListRepository = taskListRepository;
        this.circleRepository = circleRepository;
        this.taskListMapper = taskListMapper;
    }

    public List<TaskListDTO> findAllTaskLists() {
        return taskListMapper.toDTO(taskListRepository.findAll());
    }

    public Optional<TaskListDTO> findById(Integer taskListId) {
        return taskListMapper.toDTO(taskListRepository.findById(taskListId));
    }

    public TaskListDTO getById(Integer taskListId) {
        return taskListMapper.toDTO(taskListRepository.getById(taskListId));
    }

    public void save(TaskListDTO taskListDTO) {
        TaskList taskList = taskListMapper.toTaskList(taskListDTO);
        taskList.setCircle(circleRepository.getById(taskListDTO.getCircleId()));

        taskListRepository.save(taskList);
    }

    public void saveNew(CircleDTO circleDTO) {
        Circle circle = circleRepository.getById(circleDTO.getCircleId());
        TaskList taskList = new TaskList(circle);
        taskListRepository.save(taskList);
    }

    public TaskListDTO getByCircleId(Integer circleId) {
        return taskListMapper.toDTO(taskListRepository.getByCircleCircleId(circleId));
    }

    public void deleteByCircleId(Integer circleId) {
        Optional<TaskList> taskList = taskListRepository.findByCircleCircleId(circleId);
        taskList.ifPresent(list -> taskListRepository.deleteById(list.getTaskListId()));
     }
}

