package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

public class ServiceFactory {
    public static CommonService<Film> getFilmService() {
        return new FilmService();
    }

    public static CommonService<User> getUserService() {
        return new UserService();
    }
}
