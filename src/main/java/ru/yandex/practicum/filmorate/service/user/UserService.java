package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    public User update(User user) throws NotFoundException {
        if (Objects.isNull(userStorage.getUserById(user.getId()))) {
            log.warn("tried to update non-existing user");

            throw new NotFoundException("There is no such user");
        }

        userStorage.updateUser(user);

        return user;
    }

    public User getUser(int userId) throws NotFoundException {
        User user = userStorage.getUserById(userId);

        if (Objects.isNull(user)) {
            log.warn("no user with user_id:{}", userId);

            throw new NotFoundException("no user with user_id:{" + userId + "}");
        }

        return user;
    }

    public User addFriend(int userId, int friendId) {
        User user;

        try {
            user = userStorage.addFriend(userId, friendId);
        } catch (NullPointerException e) {
            log.warn("tried to add friend from non-existing user or wrong friend_id input");

            throw new NotFoundException("wrong user_id or friend_id");
        }

        return user;
    }

    public User deleteFriend(int userId, int friendId) {
        User user;

        try {
            user = userStorage.deleteFriend(userId, friendId);
        } catch (NullPointerException e) {
            log.warn("tried to delete friend from non-existing user or wrong friend_id input");

            throw new NotFoundException("wrong user_id or friend_id");
        }

        return user;
    }

    public Set<User> getFriends(int id) {
        return userStorage.getFriendsByUserId(id);
    }

    public List<User> getSharedFriendsList(int id, int friendId) {
        List<User> sharedFriends;

        try {
            sharedFriends = userStorage.getMutualFriends(id, friendId);

        } catch (NullPointerException e) {
            throw new NotFoundException("no user with user_id:{" + id + "}");
        }

        return sharedFriends;
    }
}
