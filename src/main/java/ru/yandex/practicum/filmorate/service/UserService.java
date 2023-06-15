package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchModelException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if (Objects.isNull(userStorage.getUser(user.getId()))) {
            log.warn("tried to update non-existing user");

            throw new NoSuchModelException("There is no such user");
        }

        userStorage.updateUser(user);

        return user;
    }

    public User getUser(Long userId) throws NoSuchModelException {
        User user = userStorage.getUser(userId);

        if (Objects.isNull(user)) {
            log.warn("no user with user_id:{}", userId);

            throw new NoSuchModelException("no user with user_id:{" + userId + "}");
        }

        return user;
    }

    public User addFriend(Long userId, Long friendId) {
        User user;

        try {
            user = userStorage.addFriend(userId, friendId);
        } catch (NullPointerException e) {
            log.warn("tried to add friend from non-existing user or wrong friend_id input");

            throw new NoSuchModelException("wrong user_id or friend_id");
        }

        return user;
    }

    public User deleteFriend(Long userId, Long friendId) {
        User user;

        try {
            user = userStorage.deleteFriend(userId, friendId);
        } catch (NullPointerException e) {
            log.warn("tried to delete friend from non-existing user or wrong friend_id input");

            throw new NoSuchModelException("wrong user_id or friend_id");
        }

        return user;
    }

    public List<User> getFriends(Long id) {
        return userStorage.getUsers().stream()
                .filter(user -> user.getFriends().contains(id))
                .collect(Collectors.toList());
    }

    public List<User> getSharedFriendsList(Long id, Long friendId) {
        List<User> sharedFriends = new ArrayList<>();

        try {
            for (Long userId : userStorage.getUser(id).getFriends()) {
                if (getUser(friendId).getFriends().contains(userId)) {
                    sharedFriends.add(getUser(userId));
                }
            }

        } catch (NullPointerException e) {
            throw new NoSuchModelException("no user with user_id:{" + id + "}");
        }

        return sharedFriends;
    }
}
