package ru.yandex.practicum.filmorate.util.validator.string.whitespace;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
 * if value() == true -> Whitespace is valid
 * else -> Whitespace is not valid
 * */

@Documented
@Target({FIELD, ANNOTATION_TYPE, TYPE, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = WhitespaceValidator.class)
public @interface Whitespace {
    String message() default "{Whitespace.invalid}";

    boolean value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
