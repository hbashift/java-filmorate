package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.CommonService;
import ru.yandex.practicum.filmorate.service.ServiceFactory;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final CommonService<User> service = ServiceFactory.getUserService();

    @GetMapping
    public List<User> getUsers() {
        log.info("GET /users {}", service.getAll());

        return service.getAll();
    }

    @PostMapping
    public User newUser(final @Valid @RequestBody User user) {
        User output = service.create(user);
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
