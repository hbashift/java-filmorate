package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private Long userId = 1L;

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User addUser(User user) {
        if (users.containsValue(user)) {
            log.warn("tried to add already existing user");

            throw new AlreadyExistsException("user with user_id:{" + userId + "} already exists");
        }

        user.setId(userId++);

        users.put(user.getId(), user);
        log.info("user:{} added to the storage", user.getName());

        return user;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        log.info("Пользователь с user_id:{} обновлен", user.getId());

        return user;
    }

    @Override
    public User addFriend(Long id, Long friendId) {
        users.get(id).getFriends().add(friendId);
        users.get(friendId).getFriends().add(id);
        log.info("Пользователи с user_id:{} и {} теперь друзья", id, friendId);

        return users.get(id);
    }

    @Override
    public User deleteFriend(Long id, Long friendId) {
        users.get(id).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(id);
        log.info("Пользователи с user_id:{} и {} перестали быть друзьями", id, friendId);

        return users.get(id);
    }

    @Override
    public User getUser(Long id) {
        return users.get(id);
    }
}
