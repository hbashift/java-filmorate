package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserDbStorageTest {
    private final UserDbStorage userDbStorage;
    private final JdbcTemplate jdbcTemplate;
    User user;
    User friend;
    User mutualFriend;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.update("DELETE FROM users");
        jdbcTemplate.update("DELETE FROM friendship");
        user = User.builder()
                .email("mail@mail.mail")
                .login("login")
                .birthday(LocalDate.of(1999, 8, 17))
                .build();
        user.setFriends(new HashSet<>());

        friend = User.builder()
                .email("gmail@gmail.gmail")
                .login("nelogin")
                .birthday(LocalDate.of(2001, 6, 19))
                .build();
        friend.setFriends(new HashSet<>());

        mutualFriend = User.builder()
                .email("mutual@mutual.mutual")
                .login("mutual")
                .birthday(LocalDate.of(2001, 1, 11))
                .build();
        mutualFriend.setFriends(new HashSet<>());
    }

    @Test
    void shouldCreateAndUpdateAndGetUser() {
        userDbStorage.addUser(user);
        assertEquals(user, userDbStorage.getUserById(user.getId()));
        assertEquals(user.getLogin(), userDbStorage.getUserById(user.getId()).getName());

        user.setEmail("lol@lol.lol");
        userDbStorage.updateUser(user);
        assertEquals(user, userDbStorage.getUserById(user.getId()));

        assertEquals(1, userDbStorage.getUsers().size());
        assertEquals(user, userDbStorage.getUserById(user.getId()));
    }

    @Test
    void shouldAddAndDeleteFriends() {
        userDbStorage.addUser(user);
        userDbStorage.addUser(friend);
        userDbStorage.addFriend(user.getId(), friend.getId());
        assertEquals(1, userDbStorage.getFriendsByUserId(user.getId()).size());
        assertEquals(0, userDbStorage.getFriendsByUserId(friend.getId()).size());

        userDbStorage.deleteFriend(user.getId(), friend.getId());
        assertEquals(0, userDbStorage.getFriendsByUserId(user.getId()).size());
        assertEquals(0, userDbStorage.getFriendsByUserId(friend.getId()).size());
    }

    @Test
    void shouldGetMutualFriends() {
        userDbStorage.addUser(user);
        userDbStorage.addUser(friend);
        userDbStorage.addUser(mutualFriend);
        userDbStorage.addFriend(user.getId(), mutualFriend.getId());
        userDbStorage.addFriend(friend.getId(), mutualFriend.getId());
        assertSame(userDbStorage.getMutualFriends(user.getId(), friend.getId()).get(0).getId(), mutualFriend.getId());
    }
}