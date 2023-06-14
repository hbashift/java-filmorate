package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.util.exception.BadRequestException;


@Service
public class ExceptionService {
    public void throwBadRequest(String message) {
        throw new BadRequestException(message);
    }
}