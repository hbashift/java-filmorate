package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.util.exception.UserAlreadyExistsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
class UserService implements Service<User> {
    private final Map<Integer, User> users;
    private int id;

    public UserService() {
        users = new HashMap<>();
        id = 0;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) throws UserAlreadyExistsException {
        user.setId(++id);

        if (user.getName() == null)
            user.setName(user.getLogin());

        if (users.containsValue(user)) {
            throw new UserAlreadyExistsException("Such user already exists");
        }

        users.put(id, user);

        return user;
    }

    @Override
    public User update(User user) throws NoSuchUserException {
        if (!users.containsKey(user.getId())) {
            throw new NoSuchUserException("There is no such user");
        }

        users.put(user.getId(), user);

        return user;
    }
}
