package uk.bit1.spring_jpa.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uk.bit1.spring_jpa.entity.CustomerUpdateDto;
import uk.bit1.spring_jpa.repository.CustomerRepository;

@Component
@RequiredArgsConstructor
public class CustomerLockedValidator implements ConstraintValidator<CustomerLocked, CustomerUpdateDto> {

    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(CustomerUpdateDto customerUpdateDto, ConstraintValidatorContext context) {
        if (customerUpdateDto.getVersion() == null) {
            return true; // Let @NotBlank handle this
        }
        CustomerUpdateDto savedCustomer = customerRepository.findById(customerUpdateDto.getId()).get();
        return customerUpdateDto.getVersion().equals(savedCustomer.getVersion());
    }
}
