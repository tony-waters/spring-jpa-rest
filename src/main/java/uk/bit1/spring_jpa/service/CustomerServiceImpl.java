package uk.bit1.spring_jpa.service;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.bit1.spring_jpa.service.dto.CustomerDetailUpdateDto;
import uk.bit1.spring_jpa.entity.ContactInfo;
import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.repository.CustomerRepository;
import uk.bit1.spring_jpa.repository.projection.CustomerDetailView;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;
import uk.bit1.spring_jpa.service.mapper.CustomerMapper;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional // (readOnly = true)
public class CustomerServiceImpl /*implements CustomerService*/ {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public Page<CustomerWithOrderCountView> listCustomers(Pageable pageable) {
        return customerRepository.findCustomersAndOrderCount(pageable);
    }

    public Page<OrderWithProductCountView> listOrdersForCustomer(Pageable pageable) {
        return null;
    }

    public CustomerDetailView getCustomerDetails(Long id) {
        Optional<Customer> customer = customerRepository.findWithContactInfoById(id);

        return null;
    }

    @Transactional
    public CustomerDetailUpdateDto updateCustomer(long id, CustomerDetailUpdateDto dto) {
        Customer customer = customerRepository.findById(id).orElseThrow();

        // Optional but explicit check
        if (!Objects.equals(customer.getVersion(), dto.getVersion())) {
            throw new OptimisticLockingFailureException("Stale update");
        }

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());

        ContactInfo contactInfo = customer.getContactInfo();
        contactInfo.setEmail(dto.getEmail());
        contactInfo.setPhoneNumber(dto.getPhoneNumber());

        // JPA will also enforce @Version on flush
//        return mapToDto(customer);
        return null;
    }


    public void deleteCustomerById(Long id) {

    }

    public CustomerDetailUpdateDto createCustomer(CustomerDetailUpdateDto customerDto) {
        Customer customer = new Customer(customerDto.getLastName(), customerDto.getFirstName());
        ContactInfo contactInfo = new ContactInfo(customerDto.getEmail(), customerDto.getPhoneNumber());
        customer.setContactInfo(contactInfo);
        customerRepository.save(customer);
        // convert using mappers
        return customerDto;
    }


}
