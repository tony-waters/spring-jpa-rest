package uk.bit1.spring_jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.bit1.spring_jpa.repository.CustomerRepository;
import uk.bit1.spring_jpa.repository.projection.CustomerDetailView;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;

@Service
@Transactional // (readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<CustomerWithOrderCountView> listCustomers(Pageable pageable) {
        return customerRepository.findCustomersAndOrderCount(pageable);
    }

    @Override
    public Page<OrderWithProductCountView> listOrdersForCustomer(Pageable pageable) {
        return null;
    }

    @Override
    public CustomerDetailView getCustomerDetails(Long id) {
        return null;
    }

    @Override
    public CustomerDetailView updateCustomerDetails(CustomerDetailView customerDetail) {
        return null;
    }

    @Override
    public void deleteCustomerById(Long id) {

    }

    @Override
    public CustomerDetailView addCustomer(CustomerDetailView customerDetail) {
        return null;
    }


}
