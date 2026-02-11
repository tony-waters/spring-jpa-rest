package uk.bit1.spring_jpa.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = SentenceValidator.class)
public @interface Sentence {
    String message() default "Only letters, spaces, and punctuation allowed here";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
