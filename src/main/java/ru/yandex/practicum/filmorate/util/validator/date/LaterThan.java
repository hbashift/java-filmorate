package ru.yandex.practicum.filmorate.util.validator.date;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
* if the annotated date is later than value() -> valid
* else -> non-valid
* */

@Documented
@Target({FIELD, ANNOTATION_TYPE, TYPE, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = LaterThanValidator.class)
public @interface LaterThan {

    String message() default "{LaterThan.invalid}";

    String value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}