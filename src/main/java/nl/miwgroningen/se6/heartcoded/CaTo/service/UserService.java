package nl.miwgroningen.se6.heartcoded.CaTo.service;

import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Gets data from the user repository and gives it to the controllers.
 */

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        List<User> findUsers = userRepository.findAll();
        List<User> allUsers = new ArrayList<>();

        for (User user : findUsers) {
            allUsers.add(new User(user.getUserId(), user.getName(), user.getEmail()));
        }
        return allUsers;
    }

    public User getById(Integer userId) {
        User user = new User();
        User userTemp = userRepository.getById(userId);
        user.setUserId(userTemp.getUserId());
        user.setName(userTemp.getName());
        user.setEmail(userTemp.getEmail());

        return user;
    }

    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

}
