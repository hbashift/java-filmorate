package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.serializer.DurationSerializer;
import ru.yandex.practicum.filmorate.validator.date.LaterThan;
import ru.yandex.practicum.filmorate.validator.duration.PositiveDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Film {
    @EqualsAndHashCode.Include
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
    @PositiveDuration
    @JsonSerialize(using = DurationSerializer.class)
    private Duration duration;
}
