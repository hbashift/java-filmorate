package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.yandex.practicum.filmorate.util.validator.string.whitespace.Whitespace;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class User {
    private int id;

    @NonNull
    @NotBlank
    @Email
    private String email;

    @NonNull
    @NotBlank
    @Whitespace(false)
    private String login;

    private String name;

    @NonNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
