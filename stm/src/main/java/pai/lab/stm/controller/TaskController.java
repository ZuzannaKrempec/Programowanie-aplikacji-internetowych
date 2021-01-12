package pai.lab.stm.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pai.lab.stm.dao.TaskDao;
import pai.lab.stm.dao.UserDao;
import pai.lab.stm.model.Task;
import pai.lab.stm.model.TaskStatus;
import pai.lab.stm.model.TaskType;

import java.util.List;

@RestController
@RequestMapping("/task")
@Api(value = "Task Controller", tags = {"STM"})
public class TaskController {

    private final TaskDao taskDao;
    private final UserDao userDao;

    @Autowired
    public TaskController(TaskDao taskDao, UserDao userDao) {
        this.taskDao = taskDao;
        this.userDao = userDao;
    }

    @PostMapping
    public Task addTask(@RequestBody Task task,
                        @RequestParam(name = "user", required = false) Integer id) {
        return taskDao.addTask(task, userDao.getById(id));
    }

    @GetMapping("all")
    public List<Task> getAll() {
        return taskDao.getAll();
    }

    @GetMapping
    public List<Task> getBy(@RequestParam(name = "title", required = false) String title,
                            @RequestParam(name = "status", required = false) TaskStatus status,
                            @RequestParam(name = "type", required = false) TaskType type) {
        return taskDao.getBy(title, status, type);
    }

    @PutMapping("status")
    public Task changeStatus(@RequestParam(name = "id") Integer id,
                             @RequestParam(name = "status") TaskStatus status) {
        return taskDao.changeStatus(id, status);
    }

    @DeleteMapping
    public boolean delete(@RequestParam(name = "id") Integer id) {
        return taskDao.delete(id);
    }
}
