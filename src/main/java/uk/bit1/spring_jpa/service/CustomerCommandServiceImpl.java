package uk.bit1.spring_jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.repository.CustomerRepository;
import uk.bit1.spring_jpa.service.dto.CustomerDetailCreateDto;
import uk.bit1.spring_jpa.service.dto.CustomerDetailDto;
import uk.bit1.spring_jpa.service.exception.ConflictException;
import uk.bit1.spring_jpa.service.exception.NotFoundException;
import uk.bit1.spring_jpa.service.mapper.CustomerMapper;

import jakarta.persistence.OptimisticLockException;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerCommandServiceImpl implements CustomerCommandService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDetailDto createCustomer(CustomerDetailCreateDto request) {
        Customer entity = customerMapper.toEntity(request);
        Customer saved = customerRepository.save(entity);
        return customerMapper.toDetailDto(saved);
    }

    @Override
    public CustomerDetailDto updateCustomerDetails(long id, CustomerDetailDto request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer", id));

        try {
            // MapStruct should copy allowed fields + version
            customerMapper.updateEntityFromDto(request, customer);
            Customer saved = customerRepository.save(customer);
            return customerMapper.toDetailDto(saved);
        } catch (OptimisticLockException e) {
            throw new ConflictException("Customer was modified by someone else: " + id, e);
        }
    }

    @Override
    public void deleteCustomerById(long id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException("Customer", id);
        }
        customerRepository.deleteById(id);
    }
}
