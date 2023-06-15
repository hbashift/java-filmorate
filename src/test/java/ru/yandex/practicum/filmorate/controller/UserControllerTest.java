package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {
    UserController controller;
    UserStorage userStorage;
    UserService userService;
    User testUser;

    @BeforeEach
    protected void init() {
        userStorage = new InMemoryUserStorage();
        userService = new UserService(userStorage);
        controller = new UserController(userService);

        testUser = User.builder()
                .name("John")
                .email("john@mail.ru")
                .login("login")
                .birthday(LocalDate.of(1987, 4, 14))
                .build();

    }

    @Test
    public void createUser_NameIsBlank_NameIsLoginTest() {
        testUser.setName("");
        controller.addUser(testUser);
        assertEquals("login", controller.getUsers().get(0).getName());
    }

    @Test
    void createUser_BirthdayInFuture_badRequestTest() {
        testUser.setBirthday(LocalDate.parse("2024-10-12"));
        try {
            controller.addUser(testUser);
        } catch (ValidationException e) {
            assertEquals("Неверно указана дата рождения", e.getMessage());
        }
    }

}