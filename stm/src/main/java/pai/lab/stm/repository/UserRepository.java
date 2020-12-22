package pai.lab.stm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pai.lab.stm.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
