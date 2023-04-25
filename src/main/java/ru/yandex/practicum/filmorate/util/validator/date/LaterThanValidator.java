package ru.yandex.practicum.filmorate.util.validator.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LaterThanValidator implements ConstraintValidator<LaterThan, String> {
    private LocalDate minDate;

    @Override
    public void initialize(final LaterThan annotationValue) {
        this.minDate = LocalDate.parse(annotationValue.value(), DateTimeFormatter.ISO_DATE);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        LocalDate date;

        date = LocalDate.parse(value, DateTimeFormatter.ISO_DATE);

        return compare(date);
    }

    private boolean compare(LocalDate value) {
        return value.isAfter(minDate);
    }
}
