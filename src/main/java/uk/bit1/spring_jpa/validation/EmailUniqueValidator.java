package uk.bit1.spring_jpa.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uk.bit1.spring_jpa.repository.ContactInfoRepository;

@Component
@RequiredArgsConstructor
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    private final ContactInfoRepository contactInfoRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true; // Let @NotBlank handle this
        }
        return !contactInfoRepository.existsByEmailIgnoreCase(email);
    }
}
