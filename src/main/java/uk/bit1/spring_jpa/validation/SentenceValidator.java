package uk.bit1.spring_jpa.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SentenceValidator implements ConstraintValidator<Sentence, String> {

    @Override
    public boolean isValid(String sentence, ConstraintValidatorContext context) {
        if (sentence == null) {
            return true; // Let @NotBlank handle this
        }
        String regex = "^[\\p{L}\\p{P}\\s]+$";
        return sentence.matches(regex);
    }
}
