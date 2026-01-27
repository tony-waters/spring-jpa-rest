package uk.bit1.spring_jpa.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.bit1.spring_jpa.repository.CustomerRepository;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCount;
import uk.bit1.spring_jpa.service.CustomerQueryService;

@Service
@Transactional(readOnly = true)
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final CustomerRepository customerRepository;

    public CustomerQueryServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Page<CustomerWithOrderCount> listCustomers(Pageable pageable) {
        // Intentionally thin: delegates to repository projection paging
        return customerRepository.findCustomersAndOrderCount(pageable);
    }
}
