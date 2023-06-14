package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchModelException;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;


    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addUser(User user) throws AlreadyExistsException {
        if (Objects.isNull(user.getName()) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        return userStorage.addUser(user);
    }

    public User update(User user) throws NoSuchModelException {
        if (!userStorage.contains(user)) {
            log.warn("tried to update non-existing user");

            throw new NoSuchModelException("There is no such user");
        }

        userStorage.updateUser(user);

        return user;
    }

    public User getUser(Long userId) throws NoSuchModelException {
        User output = userStorage.getUser(userId);

        if (Objects.isNull(output)) {
            log.warn("no user with user_id:{}", userId);

            throw new NoSuchModelException("no user with user_id:{" + userId + "}");
        }

        return output;
    }

    public User addFriend(Long userId, Long friendId) {
        User output;

        try {
            output = userStorage.addFriend(userId, friendId);
        } catch (NullPointerException e) {
            log.warn("tried to add friend from non-existing user or wrong friend_id input");

            throw new NoSuchModelException("wrong user_id or friend_id");
        }

        return output;
    }

    public User deleteFriend(Long userId, Long friendId) {
        User output;

        try {
            output = userStorage.deleteFriend(userId, friendId);
        } catch (NullPointerException e) {
            log.warn("tried to delete friend from non-existing user or wrong friend_id input");

            throw new NoSuchModelException("wrong user_id or friend_id");
        }

        return output;
    }

    public List<User> getFriends(Long id) {
        return userStorage.getFriends(id);
    }

    public List<User> getSharedFriendsList(Long userId, Long friendId) {
        List<User> output;

        try {
            output = userStorage.getSharedFriendsList(userId, friendId);
        } catch (NullPointerException e) {
            throw new NoSuchModelException("no user with user_id:{" + userId + "}");
        }

        return output;
    }
}
