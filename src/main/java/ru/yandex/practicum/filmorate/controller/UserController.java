package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @GetMapping
    public List<User> getUsers() {
        log.info("GET /users {}", service.getUsers());

        return service.getUsers();
    }

    @PostMapping
    public User newUser(final @Valid @RequestBody User user) {
        User output = service.addUser(user);
        log.info("POST /users {}", output);

        return output;
    }

    @PutMapping
    public User updateUser(final @Valid @RequestBody User user) {
        User output = service.update(user);
        log.info("PUT /users {}", output);

        return user;
    }
}
