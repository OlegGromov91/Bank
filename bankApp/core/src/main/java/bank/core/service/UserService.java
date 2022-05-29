package bank.core.service;

import bank.data.dto.security.user.UserDto;
import bank.data.exceptions.UserNotFoundException;
import bank.data.mapper.UserMapper;
import bank.data.model.security.user.User;
import bank.data.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @PreAuthorize("hasAuthority('user:read')")
    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }

}
