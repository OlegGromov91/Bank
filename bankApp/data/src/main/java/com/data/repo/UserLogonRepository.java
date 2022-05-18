package com.data.repo;

import com.data.model.security.user.User;
import com.data.model.security.user.UserLogon;
import com.data.model.security.user.RoleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserLogonRepository extends CrudRepository<UserLogon, Long> {


    UserLogon findByUserAndRoleType(User user, RoleType roleType);

    Optional<UserLogon> findByUserAndUserLogin(User user, String userLogin);

}
