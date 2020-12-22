package pai.lab.stm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pai.lab.stm.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
