package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.GroupHasUsersDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupHasUsersRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.GroupHasUsersDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 * <p>
 * Dit is wat het programma doet
 */

@Service
public class GroupHasUsersService {

    GroupHasUsersDTOConverter groupHasUsersDTOConverter;

    GroupRepository groupRepository;
    UserRepository userRepository;
    GroupHasUsersRepository groupHasUsersRepository;
    TaskListRepository taskListRepository;

    public GroupHasUsersService(GroupHasUsersDTOConverter groupHasUsersDTOConverter, GroupRepository groupRepository, UserRepository userRepository, GroupHasUsersRepository groupHasUsersRepository, TaskListRepository taskListRepository) {
        this.groupHasUsersDTOConverter = groupHasUsersDTOConverter;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupHasUsersRepository = groupHasUsersRepository;
        this.taskListRepository = taskListRepository;
    }

    public void saveGroupHasUsers(GroupHasUsers groupHasUsers) {
        groupHasUsersRepository.save(groupHasUsers);
    }

    public List<GroupHasUsers> getAllByGroupId(Integer groupId) {
        return groupHasUsersRepository.getAllByGroup(groupRepository.getById(groupId));
    }

    @Transactional
    public void deleteByUserId(Integer userId, Integer groupId) {
        groupHasUsersRepository.deleteByUserAndGroup(userRepository.getById(userId), groupRepository.getById(groupId));
    }

    public Optional<GroupHasUsers> findByUserIdAndGroupId(Integer userId, Integer groupId) {
//        Optional<GroupHasUsers> groupHasUsers = groupHasUsersRepository.findGroupHasUsersByUserAndGroup(
//                userRepository.getById(userId),
//                groupRepository.getById(groupId))
//
//        if (groupHasUsers.isPresent()) {
//            Optional<GroupHasUsersDTO> result = groupHasUsersDTOConverter.toDTO(groupHasUsers.get());
//        }
        return groupHasUsersRepository.findGroupHasUsersByUserAndGroup(
                userRepository.getById(userId),
                groupRepository.getById(groupId));
    }

    public List<Group> getAllGroupsByUserId(Integer userId) {
        ArrayList<Group> groupsByUser = new ArrayList<>();
        for (GroupHasUsers groupHasUsers : groupHasUsersRepository.getAllByUser(userRepository.getById(userId))) {
            groupsByUser.add(groupHasUsers.getGroup());
        }
        return groupsByUser;
    }

    public List<GroupHasUsers> findAllClients() {
        List<GroupHasUsers> allClients = new ArrayList<>();
        for (GroupHasUsers groupHasUsers : groupHasUsersRepository.findAll()) {
            if (groupHasUsers.getUserRole().equals("Client")) {
                allClients.add(groupHasUsers);
            }
        }
        return allClients;
    }

    public GroupHasUsers getByClient(User client) {
        List<GroupHasUsers> allGroupHasUsers = groupHasUsersRepository.getAllByUser(client);
        GroupHasUsers clientGroupHasUsers = new GroupHasUsers();
        for (GroupHasUsers groupHasUsers : allGroupHasUsers) {
            if (groupHasUsers.getUserRole().equals("Client")) {
                clientGroupHasUsers = groupHasUsers;
            }
        }
        return clientGroupHasUsers;
    }

    public boolean userInGroupExists(GroupHasUsers groupHasUsers) {
        if (findByUserIdAndGroupId(
                groupHasUsers.getUser().getUserId(),
                groupHasUsers.getGroup().getGroupId())
                .isPresent()){
                return true;
            }
        return false;
    }

    public List<GroupHasUsers> getGroupAdminsByGroupId(Integer groupId) {
        List<GroupHasUsers> allFromGroup = getAllByGroupId(groupId);
        List<GroupHasUsers> groupHasUsersIsAdmin = new ArrayList<>();

        for (GroupHasUsers groupHasUsers : allFromGroup) {
            if (groupHasUsers.isAdmin()) {
                groupHasUsersIsAdmin.add(groupHasUsers);
            }
        }
        return groupHasUsersIsAdmin;
    }

    public boolean findOutIfGroupHasUsersIsAdmin(GroupHasUsers groupHasUsers) {
        return findByUserIdAndGroupId(groupHasUsers.getUser().getUserId(),
                groupHasUsers.getGroup().getGroupId()).get().isAdmin();
    }

    public boolean isClientInOtherGroup(User user, Integer groupId) {
        boolean isClient = false;
        List<GroupHasUsers> allClients = findAllClients();

        for (GroupHasUsers client : allClients) {
            if (Objects.equals(user.getUserId(), client.getUser().getUserId()) && !Objects.equals(client.getGroup().getGroupId(), groupId)) {
                isClient = true;
                break;
            }
        }
        return isClient;
    }
}
