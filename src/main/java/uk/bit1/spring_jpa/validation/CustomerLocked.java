package uk.bit1.spring_jpa.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CustomerLockedValidator.class)
public @interface CustomerLocked {
    String message() default "Customer has changed since you last read it";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
