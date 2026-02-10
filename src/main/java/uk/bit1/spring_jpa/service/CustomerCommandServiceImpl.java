package uk.bit1.spring_jpa.service;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.repository.CustomerRepository;
import uk.bit1.spring_jpa.service.dto.CustomerDetailCreateDto;
import uk.bit1.spring_jpa.service.dto.CustomerDetailDto;
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
    public CustomerDetailDto createCustomer(CustomerDetailCreateDto request) {
        Customer entity = customerMapper.toEntity(request);
//        entity.setContactInfo(new ContactInfo(request.email(), request.phoneNumber()));
        Customer saved = customerRepository.save(entity);
        return customerMapper.toDetailDto(saved);
    }

    @Override
    public CustomerDetailDto updateCustomerDetails(long id, CustomerDetailDto customerDetailDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found: id=" + id));

//        if (customerDetailDto.version() == null) {
//            throw new ConflictException("Missing version for update: id=" + id, new IllegalArgumentException());
//        }
//        if (customer.getVersion() != customerDetailDto.version()) {
//            throw new ConflictException("Stale version for customer id=" + id
//                    + " (expected " + customer.getVersion()
//                    + ", got " + customerDetailDto.version() + ")", new OptimisticLockException());
//        }


        try {
            // MapStruct should copy allowed fields + version
            customerMapper.updateEntityFromDto(customerDetailDto, customer);
            Customer saved = customerRepository.save(customer);
            return customerMapper.toDetailDto(saved);
        } catch (OptimisticLockException e) {
            throw new ConflictException("Customer was modified by someone else: " + id, e);
        }
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
