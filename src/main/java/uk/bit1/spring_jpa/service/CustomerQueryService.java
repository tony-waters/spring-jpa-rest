package uk.bit1.spring_jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.bit1.spring_jpa.controller.CustomerRestRequirements;
import uk.bit1.spring_jpa.repository.CustomerRepository;
import uk.bit1.spring_jpa.repository.projection.CustomerDetailView;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;
//import uk.bit1.spring_jpa.service.CustomerQueryService;

@Service
@Transactional(readOnly = true)
public class CustomerQueryService implements CustomerRestRequirements {

    private final CustomerRepository customerRepository;

    public CustomerQueryService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<CustomerWithOrderCountView> listCustomers(Pageable pageable) {
        // Intentionally thin: delegates to repository projection paging
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
