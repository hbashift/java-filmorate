package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class Mpa {

    @NotNull
    @Positive
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

}