package bank.data.repo;

import bank.data.model.security.user.User;
import bank.data.model.security.user.UserLogon;
import bank.data.model.security.user.RoleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserLogonRepository extends CrudRepository<UserLogon, Long> {


    UserLogon findByUserAndRoleType(User user, RoleType roleType);

    Optional<UserLogon> findByUserAndUserLogin(User user, String userLogin);

}
