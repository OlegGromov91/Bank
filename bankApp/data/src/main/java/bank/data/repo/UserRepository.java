package bank.data.repo;

import bank.data.model.security.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUserLogin(String userLogin);

}
