package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.util.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.util.exception.NoSuchModelException;

import java.util.List;

public interface Service<T> {
    List<T> getAll();

    T create(T model) throws AlreadyExistsException;

    T update(T model) throws NoSuchModelException;
}
