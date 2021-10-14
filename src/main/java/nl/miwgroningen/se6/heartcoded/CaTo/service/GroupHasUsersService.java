package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupHasUsersRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 * <p>
 * Dit is wat het programma doet
 */

@Service
public class GroupHasUsersService {

    GroupRepository groupRepository;
    UserRepository userRepository;
    GroupHasUsersRepository groupHasUsersRepository;

    public GroupHasUsersService(GroupRepository groupRepository,
                                UserRepository userRepository,
                                GroupHasUsersRepository groupHasUsersRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupHasUsersRepository = groupHasUsersRepository;
    }

    public void saveGroupHasUsers(GroupHasUsers groupHasUsers) {
        groupHasUsersRepository.save(groupHasUsers);
    }

    public List<GroupHasUsers> getAllByGroupId(Integer groupId) {
        return groupHasUsersRepository.getAllByGroup(groupRepository.getById(groupId));
    }

    @Transactional
    public void deleteByUserId(Integer userId, Integer groupId) {
        User user = userRepository.getById(userId);
        Group group = groupRepository.getById(groupId);
        groupHasUsersRepository.deleteByUserAndGroup(user, group);
    }
}
