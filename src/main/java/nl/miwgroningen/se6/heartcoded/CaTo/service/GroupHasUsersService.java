package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.GroupHasUsers;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupHasUsersRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 * <p>
 * Dit is wat het programma doet
 */

@Service
public class GroupHasUsersService {

    GroupRepository groupRepository;

    GroupHasUsersRepository groupHasUsersRepository;

    public GroupHasUsersService(GroupRepository groupRepository, GroupHasUsersRepository groupHasUsersRepository) {
        this.groupRepository = groupRepository;
        this.groupHasUsersRepository = groupHasUsersRepository;
    }

    public void saveGroupHasUsers(GroupHasUsers groupHasUsers) {
        groupHasUsersRepository.save(groupHasUsers);
    }
}
