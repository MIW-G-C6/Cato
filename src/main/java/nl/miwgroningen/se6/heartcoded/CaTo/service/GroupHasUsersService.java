package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupHasUsersRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.GroupDTOConverter;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.GroupHasUsersDTOConverter;
import nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter.UserDTOConverter;
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

    private final GroupHasUsersDTOConverter groupHasUsersDTOConverter;
    private final GroupDTOConverter groupDTOConverter;
    private final UserDTOConverter userDTOConverter;

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupHasUsersRepository groupHasUsersRepository;
    private final TaskListRepository taskListRepository;

    public GroupHasUsersService(GroupHasUsersDTOConverter groupHasUsersDTOConverter,
                                GroupDTOConverter groupDTOConverter,
                                UserDTOConverter userDTOConverter,
                                GroupRepository groupRepository,
                                UserRepository userRepository,
                                GroupHasUsersRepository groupHasUsersRepository,
                                TaskListRepository taskListRepository) {
        this.groupHasUsersDTOConverter = groupHasUsersDTOConverter;
        this.groupDTOConverter = groupDTOConverter;
        this.userDTOConverter = userDTOConverter;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupHasUsersRepository = groupHasUsersRepository;
        this.taskListRepository = taskListRepository;
    }

    public void saveGroupHasUsers(GroupHasUsersDTO groupHasUsers) {
        groupHasUsersRepository.save(groupHasUsersDTOConverter.toModel(groupHasUsers));
    }

    public List<GroupHasUsersDTO> getAllByGroupId(Integer groupId) {
        return groupHasUsersDTOConverter.toDTOList(groupHasUsersRepository.
                getAllByGroup(groupRepository.getById(groupId)));
    }

    @Transactional
    public void deleteByUserId(Integer userId, Integer groupId) {
        groupHasUsersRepository.deleteByUserAndGroup(userRepository.getById(userId), groupRepository.getById(groupId));
    }

    public Optional<GroupHasUsersDTO> findByUserIdAndGroupId(Integer userId, Integer groupId) {
        Optional<GroupHasUsersDTO> result = Optional.empty();
        Optional<GroupHasUsers> groupHasUsers = groupHasUsersRepository.findGroupHasUsersByUserAndGroup(
                userRepository.getById(userId),
                groupRepository.getById(groupId));
        if(groupHasUsers.isPresent()) {
            result = Optional.of(groupHasUsersDTOConverter.toDTO(groupHasUsers.get()));
        }
        return result;
    }

    public List<GroupDTO> getAllGroupsByUserId(Integer userId) {
        List<GroupDTO> result = new ArrayList<>();
        List<GroupHasUsers> groupsHasUserList = groupHasUsersRepository.getAllByUser(userRepository.getById(userId));
        for (GroupHasUsers groupHasUsers : groupsHasUserList) {
            result.add(groupDTOConverter.toDTO(groupHasUsers.getGroup()));
        }
        return result;
    }

    public List<GroupHasUsersDTO> findAllClients() {
        List<GroupHasUsersDTO> result = new ArrayList<>();
        for (GroupHasUsers groupHasUsers : groupHasUsersRepository.findAll()) {
            if (groupHasUsers.getUserRole().equals("Client")) {
                result.add(groupHasUsersDTOConverter.toDTO(groupHasUsers));
            }
        }
        return result;
    }

    public GroupHasUsersDTO getByClient(UserDTO client) {
        List<GroupHasUsers> allGroupHasUsers = groupHasUsersRepository.getAllByUser(
                userRepository.getById(client.getUserId()));

        GroupHasUsersDTO result = new GroupHasUsersDTO();
        for (GroupHasUsers groupHasUsers : allGroupHasUsers) {
            if (groupHasUsers.getUserRole().equals("Client")) {
                result = groupHasUsersDTOConverter.toDTO(groupHasUsers);
            }
        }
        return result;
    }

    public boolean userInGroupExists(GroupHasUsersDTO groupHasUsers) {
        if (findByUserIdAndGroupId(
                groupHasUsers.getUser().getUserId(),
                groupHasUsers.getGroup().getGroupId())
                .isPresent()){
                return true;
            }
        return false;
    }

    public List<GroupHasUsersDTO> getGroupAdminsByGroupId(Integer groupId) {
        List<GroupHasUsersDTO> allFromGroup = getAllByGroupId(groupId);
        List<GroupHasUsersDTO> result = new ArrayList<>();

        for (GroupHasUsersDTO groupHasUsers : allFromGroup) {
            if (groupHasUsers.isAdmin()) {
                result.add(groupHasUsers);
            }
        }
        return result;
    }

    public boolean findOutIfGroupHasUsersIsAdmin(GroupHasUsersDTO groupHasUsers) {
        return findByUserIdAndGroupId(groupHasUsers.getUser().getUserId(),
                groupHasUsers.getGroup().getGroupId()).get().isAdmin();
    }

    public boolean isClientInOtherGroup(UserDTO user, Integer groupId) {
        boolean result = false;
        List<GroupHasUsersDTO> allClients = findAllClients();

        for (GroupHasUsersDTO client : allClients) {
            if (Objects.equals(user.getUserId(), client.getUser().getUserId()) && !Objects.equals(client.getGroup().getGroupId(), groupId)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
