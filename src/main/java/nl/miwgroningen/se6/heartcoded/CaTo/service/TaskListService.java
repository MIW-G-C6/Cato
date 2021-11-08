package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.MemberRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.TaskListMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.service.mappers.UserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    private final GroupRepository groupRepository;
    private final TaskListMapper taskListMapper;

    public TaskListService(TaskListRepository taskListRepository, GroupRepository groupRepository,
                           TaskListMapper taskListMapper) {
        this.taskListRepository = taskListRepository;
        this.groupRepository = groupRepository;
        this.taskListMapper = taskListMapper;
    }

    public Optional<TaskListDTO> findById(Integer taskListId) {
        return taskListMapper.toDTO(taskListRepository.findById(taskListId));
    }

    public TaskListDTO getById(Integer taskListId) {
        return taskListMapper.toDTO(taskListRepository.getById(taskListId));
    }

    public void save(TaskListDTO taskListDTO) {
        TaskList taskList = taskListMapper.toTaskList(taskListDTO);
        taskList.setGroup(groupRepository.getById(taskListDTO.getGroupId()));

        taskListRepository.save(taskList);
    }

    public void saveNew(GroupDTO groupDTO) {
        Group group = groupRepository.getById(groupDTO.getGroupId());
        TaskList taskList = new TaskList(group);
        taskListRepository.save(taskList);
    }

    public TaskListDTO getByGroupId(Integer groupId) {
        return taskListMapper.toDTO(taskListRepository.getByGroupGroupId(groupId));
    }

    public void deleteByGroupId(Integer groupId) {
        Optional<TaskList> taskList = taskListRepository.findByGroupGroupId(groupId);
        taskList.ifPresent(list -> taskListRepository.deleteById(list.getTaskListId()));
     }
}

