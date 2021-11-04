package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Member;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
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
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TaskListMapper taskListMapper;
    private final UserMapper userMapper;

    public TaskListService(TaskListRepository taskListRepository, UserRepository userRepository, MemberRepository memberRepository, TaskListMapper taskListMapper, UserMapper userMapper) {
        this.taskListRepository = taskListRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.taskListMapper = taskListMapper;
        this.userMapper = userMapper;
    }

    public List<TaskListDTO> findAll() {
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
        taskList.setClient(userRepository.getById(taskListDTO.getUserId()));


        taskListRepository.save(taskList);
    }

    public TaskListDTO findByUser(UserDTO user) {
        User client = userRepository.getById(user.getUserId());
        return taskListMapper.toDTO(taskListRepository.findByClient(client));
    }

    public List<TaskListDTO> getAllByGroupId(Integer groupId) {
        List<Member> allClientMembers = memberRepository.getAllByUserRoleAndGroupGroupId("Client", groupId);
        List<TaskListDTO> allTaskLists = new ArrayList<>();
        for (Member clientMember : allClientMembers) {
            allTaskLists.add(findByUser(userMapper.toDTO(userRepository.getById(clientMember.getUser().getUserId()))));
        }
        return allTaskLists;
    }
}

