package pai.lab.stm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pai.lab.stm.model.Task;
import pai.lab.stm.model.TaskStatus;
import pai.lab.stm.model.TaskType;
import pai.lab.stm.model.User;
import pai.lab.stm.repository.TaskRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskDao {

    private final TaskRepository repository;

    @Autowired
    public TaskDao(TaskRepository repository) {
        this.repository = repository;
    }

    public Task addTask(Task task, User user) {
        if (task == null) return new Task();
        task.setUser(user);
        task = repository.save(task);
        task.getUser().setTaskList(new ArrayList<>());
        return task;
    }

    public List<Task> getAll() {
        List<Task> all = repository.findAll().stream().sorted(Comparator.comparing(Task::getDateAdded))
                .collect(Collectors.toList());
        all.forEach(task -> task.getUser().setTaskList(new ArrayList<>()));
        Collections.reverse(all);
        return  all;
    }

    public List<Task> getBy(String title, TaskStatus status, TaskType type) {
        if (title == null) title = "";
        String statusStr = status == null ? "" : status.name();
        String typeStr = type == null ? "" : type.name();
        return repository.findByTitleContainingAndStatusContainingAndTypeContaining(title, statusStr, typeStr);
    }

    public Task changeStatus(Integer id, TaskStatus status) {
        Optional<Task> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus(status);
            return repository.save(task);
        } else
            return new Task();
    }

    public boolean delete(Integer id) {
        Optional<Task> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = unassign(optionalTask.get());
            repository.delete(task);
        }
        return true;
    }

    public Task unassign(Task task) {
        task.setUser(null);
        return repository.save(task);
    }
}
