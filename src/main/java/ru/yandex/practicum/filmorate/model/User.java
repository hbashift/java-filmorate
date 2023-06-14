package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.yandex.practicum.filmorate.util.validator.string.whitespace.Whitespace;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    @EqualsAndHashCode.Exclude
    private Long id;

    @NonNull
    @NotBlank(message = "null or blank email")
    @Email(message = "wrong email format")
    private String email;

    @NonNull
    @NotBlank(message = "null or blank login")
    @Whitespace(false)
    private String login;

    private String name;

    @NonNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();
}
