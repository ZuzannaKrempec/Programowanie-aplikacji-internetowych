package pai.lab.stm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pai.lab.stm.model.User;
import pai.lab.stm.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserDao {

    private final UserRepository userRepository;
    private final TaskDao taskDao;

    @Autowired
    public UserDao(UserRepository userRepository, TaskDao taskDao) {
        this.userRepository = userRepository;
        this.taskDao = taskDao;
    }

    public User addUser(User user) {
        if (user == null) return new User();

        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Integer id) {
        return userRepository.findById(id).orElse(new User());
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(new User());
    }

    public User changeStatus(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus(!user.isStatus());
            return userRepository.save(user);
        } else
            return new User();
    }

    public boolean delete(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getTaskList().forEach(taskDao::unassign);
            userRepository.delete(user);
        }
        return true;
    }
}
