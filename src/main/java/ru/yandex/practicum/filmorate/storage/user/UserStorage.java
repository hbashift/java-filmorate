package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {

    /**
     * Метод для получения списка пользователей
     */
    List<User> getUsers();

    /**
     * Метод для добавления пользователя
     */
    User addUser(User user);

    /**
     * Метод для обновления пользователя
     */
    User updateUser(User user);

    /**
     * Метод добавления в друзья
     */
    User addFriend(int userId, int friendId);

    /**
     * Метод удаления из друзей
     */
    User deleteFriend(int userId, int friendId);


    /**
     * Метод для обновления пользователя
     */

    List<User> getMutualFriends(int id, int otherId);

    /**
     * Метод для получения пользователя по id
     */

    User getUserById(int id);

    /**
     * Метод для получения списка друзей
     */
    Set<User> getFriendsByUserId(int id);

}