package uk.bit1.spring_jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.bit1.spring_jpa.service.dto.projection.CustomerDetailDto;
import uk.bit1.spring_jpa.entity.ContactInfo;
import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.repository.CustomerRepository;
import uk.bit1.spring_jpa.repository.projection.CustomerDetailView;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;

@Service
@Transactional // (readOnly = true)
public class CustomerServiceImpl /*implements CustomerService*/ {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<CustomerWithOrderCountView> listCustomers(Pageable pageable) {
        return customerRepository.findCustomersAndOrderCount(pageable);
    }

    public Page<OrderWithProductCountView> listOrdersForCustomer(Pageable pageable) {
        return null;
    }

    public CustomerDetailView getCustomerDetails(Long id) {
        return null;
    }

    public CustomerDetailView updateCustomerDetails(CustomerDetailView customerDetail) {
        return null;
    }

    public void deleteCustomerById(Long id) {

    }

    public CustomerDetailDto createCustomer(CustomerDetailDto customerDto) {
        Customer customer = new Customer(customerDto.getLastName(), customerDto.getFirstName());
        ContactInfo contactInfo = new ContactInfo(customerDto.getEmail(), customerDto.getPhoneNumber());
        customer.setContactInfo(contactInfo);
        customerRepository.save(customer);
        // convert using mappers
        return customerDto;
    }


}
