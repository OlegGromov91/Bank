package ru.sberbank.local.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.local.dto.security.user.UserDto;
import ru.sberbank.local.service.UserService;

/**
 * Контроллер разблокировки пользователей
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

}
