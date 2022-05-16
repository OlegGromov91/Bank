package ru.sberbank.local.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.sberbank.local.model.security.user.RoleType;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.model.security.user.UserLogon;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUserLogin(String userLogin);

}
