package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchModelException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
class UserService implements CommonService<User> {
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
    public User create(User user) throws AlreadyExistsException {
        user.setId(++id);

        if (user.getName() == null)
            user.setName(user.getLogin());

        if (users.containsValue(user)) {
            throw new AlreadyExistsException("Such user already exists");
        }

        users.put(id, user);

        return user;
    }

    @Override
    public User update(User user) throws NoSuchModelException {
        if (!users.containsKey(user.getId())) {
            throw new NoSuchModelException("There is no such user");
        }

        users.put(user.getId(), user);

        return user;
    }
}