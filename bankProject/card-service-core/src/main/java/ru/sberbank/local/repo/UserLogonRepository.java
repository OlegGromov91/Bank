package ru.sberbank.local.repo;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.local.model.security.user.RoleType;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.model.security.user.UserLogon;

import java.util.Optional;

public interface UserLogonRepository extends CrudRepository<UserLogon, Long> {


    UserLogon findByUserAndRoleType(User user, RoleType roleType);

    Optional<UserLogon> findByUserAndUserLogin(User user, String userLogin);

}
