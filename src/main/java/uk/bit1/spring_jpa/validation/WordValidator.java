package uk.bit1.spring_jpa.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WordValidator implements ConstraintValidator<Word, String> {

    @Override
    public boolean isValid(String word, ConstraintValidatorContext context) {
        if (word == null) {
            return true; // Let @NotBlank handle this
        }
        String regex = "^[\\p{L}]+$";
        return word.matches(regex);
    }
}
