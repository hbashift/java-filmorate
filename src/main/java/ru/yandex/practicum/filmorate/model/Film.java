package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.util.validator.date.LaterThan;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
@Builder
public class Film {
    @EqualsAndHashCode.Exclude
    private int id;

    @NonNull
    @NotBlank
    private String name;

    @NonNull
    @NotBlank
    @Length(max = 200)
    private String description;

    @NonNull
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd")
    @LaterThan("1895-12-28")
    private String releaseDate;

    @NonNull
    @NotNull
    @Positive
    private int duration;

    private Set<Integer> likes;

    private Mpa mpa;

    private Set<Genre> genres;
}
