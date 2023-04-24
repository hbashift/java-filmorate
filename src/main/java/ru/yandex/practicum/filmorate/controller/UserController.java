package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Set<User> users = new HashSet<>();
    private int id = 0;

    @GetMapping
    public Set<User> getUsers() {
        log.info("GET /users {}", users);

        return users;
    }

    @PostMapping
    public User newUser(final @Valid @RequestBody User user) throws UserAlreadyExistsException {
        user.setId(++id);

        if (user.getName() == null)
            user.setName(user.getLogin());

        if (users.stream()
                .anyMatch(
                        u -> user.getLogin().equals(u.getLogin())
                        && user.getEmail().equals(u.getEmail())
                        && user.getName().equals(u.getName())
                        && user.getBirthday().equals(u.getBirthday()))) {

            log.warn("Trying to create already existing user");
            throw new UserAlreadyExistsException("Such user already exists");
        }

        users.add(user);
        log.info("POST /users {}", user);

        return user;
    }

    @PutMapping
    public User updateUser(final @Valid @RequestBody User user) throws NoSuchUserException {
        if (users.stream()
                .noneMatch(u -> user.getId() == u.getId())) {

            log.warn("Trying to update non-existing user");
            throw new NoSuchUserException("There is no such user");
        }

        users.remove(user);
        users.add(user);
        log.info("PUT /users {}", user);

        return user;
    }
}
