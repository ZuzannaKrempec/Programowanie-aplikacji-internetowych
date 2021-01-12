package pai.lab.stm.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pai.lab.stm.dao.UserDao;
import pai.lab.stm.model.User;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(value = "User Controller", tags = {"STM"})
public class UserController {

    private final UserDao dao;

    @Autowired
    public UserController(UserDao dao) {
        this.dao = dao;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return dao.addUser(user);
    }

    @GetMapping("all")
    public List<User> getAll() {
        return dao.getAll();
    }

    @GetMapping
    public User getBy(@RequestParam(name = "id", required = false) Integer id,
                        @RequestParam(name = "email", required = false) String email) {
        if (id != null)
            return dao.getById(id);
        else if (email != null)
            return dao.getByEmail(email);
        else
            return new User();
    }

    @PutMapping("status")
    public User changeStatus(@RequestParam(name = "id") Integer id) {
        return dao.changeStatus(id);
    }

    @DeleteMapping
    public boolean delete(@RequestParam(name = "id") Integer id) {
        return dao.delete(id);
    }
}
