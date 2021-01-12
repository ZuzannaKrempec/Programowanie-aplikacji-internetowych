package pai.lab.stm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pai.lab.stm.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

//    List<Task> findAllOrderByDateAdded();

    List<Task> findByTitleContainingAndStatusContainingAndTypeContaining(String title, String status, String type);
}
