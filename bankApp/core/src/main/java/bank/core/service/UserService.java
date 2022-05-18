package bank.core.service;

import com.data.dto.security.user.UserDto;
import com.data.exceptions.UserNotFoundException;
import com.data.mapper.UserMapper;
import com.data.model.security.user.User;
import com.data.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    @PreAuthorize("hasAuthority('user:read')")
    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }

}
