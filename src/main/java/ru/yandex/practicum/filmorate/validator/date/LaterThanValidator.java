package ru.yandex.practicum.filmorate.validator.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.SneakyThrows;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LaterThanValidator implements ConstraintValidator<LaterThan, String> {
    private LocalDate minDate;
    // TODO узнать, что за SneakyThrows и как прокинуть exception по нормальному
    @SneakyThrows
    @Override
    public void initialize(final LaterThan annotationValue) {
        try {
            this.minDate = LocalDate.parse(annotationValue.value(), DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new ValidationException("date format incorrect");
        }
    }

    @SneakyThrows
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        LocalDate date;

        try {
            date = LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new ValidationException("date format is incorrect");
        }

        return compare(date);
    }

    private boolean compare(LocalDate value) {
        return value.isAfter(minDate);
    }
}
