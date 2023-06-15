package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> getUsers();

    User addUser(User user);

    User updateUser(User user);

    User addFriend(Long id, Long friendId);

    User deleteFriend(Long id, Long friendId);

    User getUser(Long id);
}
