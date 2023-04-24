package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.Service;
import ru.yandex.practicum.filmorate.service.ServiceFactory;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchModelException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.util.exception.UserAlreadyExistsException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Service<User> service = ServiceFactory.getUserService();

    @GetMapping
    public List<User> getUsers() {
        log.info("GET /users {}", service.getAll());

        return service.getAll();
    }

    @PostMapping
    public User newUser(final @Valid @RequestBody User user) throws UserAlreadyExistsException {
        User output;
        try {
            output = service.create(user);
        } catch (AlreadyExistsException e) {
            log.warn("Trying to create already existing user");
            throw new UserAlreadyExistsException("Such user already exists");
        }

        log.info("POST /users {}", output);

        return output;
    }

    @PutMapping
    public User updateUser(final @Valid @RequestBody User user) throws NoSuchUserException {
        try {
            service.update(user);
        } catch (NoSuchModelException e) {
            log.warn("Trying to update non-existing user");
            throw new NoSuchUserException("There is no such user");
        }

        log.info("PUT /users {}", user);

        return user;
    }
}
