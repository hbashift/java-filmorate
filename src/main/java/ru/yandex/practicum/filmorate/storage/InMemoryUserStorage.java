package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        user.setId(userId++);

        if (users.containsKey(user.getId())) {
            log.warn("tried to add already existing user");

            throw new AlreadyExistsException("user with user_id:{" + user.getId() + "} already exists");
        }

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
    public List<User> getSharedFriendsList(Long id, Long friendId) {
        List<User> sharedFriends = new ArrayList<>();

        for (Long userId : getUser(id).getFriends()) {
            if (getUser(friendId).getFriends().contains(userId)) {
                sharedFriends.add(getUser(userId));
            }
        }

        return sharedFriends;
    }

    @Override
    public User getUser(Long id) {
        return users.get(id);
    }

    @Override
    public List<User> getFriends(Long id) {
        return getUsers().stream()
                .filter(user -> user.getFriends().contains(id))
                .collect(Collectors.toList());
    }

    @Override
    public boolean contains(Long userId) {
        return users.containsKey(userId);
    }

    public static void main(String[] args) {
        InMemoryUserStorage userStorage = new InMemoryUserStorage();

        User user = new User("asdf@mail.ru", "login", LocalDate.of(2002, 12, 31));
        userStorage.addUser(user);
        System.out.println(userStorage.getSharedFriendsList(1L, 10L));
    }
}
