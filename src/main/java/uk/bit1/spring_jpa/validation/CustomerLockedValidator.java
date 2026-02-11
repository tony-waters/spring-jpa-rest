package uk.bit1.spring_jpa.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.repository.CustomerRepository;

@Component
@RequiredArgsConstructor
public class CustomerLockedValidator implements ConstraintValidator<CustomerLocked, Customer> {

    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(Customer customerUpdateDto, ConstraintValidatorContext context) {
        if (customerUpdateDto.getVersion() == null) {
            return true; // Let @NotBlank handle this
        }
        Customer savedCustomer = customerRepository.findById(customerUpdateDto.getId()).get();
        return customerUpdateDto.getVersion().equals(savedCustomer.getVersion());
    }
}
