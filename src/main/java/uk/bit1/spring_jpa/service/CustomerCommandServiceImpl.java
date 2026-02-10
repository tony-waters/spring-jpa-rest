package uk.bit1.spring_jpa.service;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.repository.CustomerRepository;
import uk.bit1.spring_jpa.service.dto.*;
import uk.bit1.spring_jpa.service.exception.ConflictException;
import uk.bit1.spring_jpa.service.exception.NotFoundException;
import uk.bit1.spring_jpa.service.mapper.CustomerMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerCommandServiceImpl implements CustomerCommandService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerReadDto createCustomer(CustomerCreateDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("CustomerUpdateDto cannot be null");
        }
//        if (contactInfoRepository.existsByEmailIgnoreCase(dto.email())) {
//            throw new ConflictException("Email already exists: " + dto.email());
//        }
        Customer customer = new Customer();
        customerMapper.updateEntityFromCreateDto(dto, customer);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toReadDto(saved);
    }

    @Override
    public CustomerReadDto updateCustomer(long id, CustomerUpdateDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("CustomerUpdateDto cannot be null");
        }

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found: id=" + id));

        // Client-driven optimistic locking (fast fail with a clear message)
        if (dto.version() == null) {
            throw new IllegalArgumentException("Missing version for update (id=" + id + ")");
        }

        if (customer.getVersion().equals(dto.version())) {
            throw new ConflictException(
                    "Stale version for customer id=" + id
                            + " (expected " + customer.getVersion()
                            + ", got " + dto.version() + ")"
            );
        }

        customerMapper.updateEntityFromUpdateDto(dto, customer);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toReadDto(saved);
    }

    @Override
    public void deleteCustomerById(long id) {
        try {
            customerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Customer not found: id=" + id, e);
        }
    }
}
