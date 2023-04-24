package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

public class ServiceFactory {
    public static Service<Film> getFilmService() {
        return new FilmService();
    }

    public static Service<User> getUserService() {
        return new UserService();
    }
}
