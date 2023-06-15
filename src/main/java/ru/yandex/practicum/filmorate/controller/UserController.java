package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        log.info("GET /users {}", userService.getUsers());

        return userService.getUsers();
    }

    @PostMapping
    public User addUser(final @Valid @RequestBody User user) {
        User output = userService.addUser(user);
        log.info("POST /users {}", output);

        return output;
    }

    @PutMapping
    public User updateUser(final @Valid @RequestBody User user) {
        User output = userService.update(user);
        log.info("PUT /users {}", output);

        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {

        log.info("Поступил запрос на добавление в друзья пользователей с id: {} и {}", id, friendId);
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable int id, @PathVariable int friendId) {

        log.info("От пользователя с id: {} поступил запрос на удаление из друзей пользователя с id: {}", id, friendId);
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public List<User> getSharedFriendsList(@PathVariable int id, @PathVariable int friendId) {

        log.info("Поступил запрос на получение списка общих друзей пользователей с id: {} и {}", id, friendId);
        return userService.getSharedFriendsList(id, friendId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {

        log.info("Поступил запрос на получение пользователя с id {}", id);
        return userService.getUser(id);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable int id) {

        log.info("Поступил запрос на получение списка друзей пользователя с id {}", id);
        return userService.getFriends(id);
    }
}
