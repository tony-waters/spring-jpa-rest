package uk.bit1.spring_jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.bit1.spring_jpa.repository.CustomerRepository;
//import uk.bit1.spring_jpa.repository.OrderRepository;
import uk.bit1.spring_jpa.repository.OrderRepository;
import uk.bit1.spring_jpa.repository.projection.CustomerDetailView;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;
import uk.bit1.spring_jpa.exception.NotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Override
    public Page<CustomerWithOrderCountView> listCustomers(Pageable pageable) {
        // Repository should do the projection + paging
        return customerRepository.findAllCustomerWithOrderCount(pageable);
    }

//    @Override
//    public CustomerDetailView getCustomerDetails(long id) {
//        return null;
//    }

    @Override
    public Page<OrderWithProductCountView> listOrdersForCustomer(long customerId, Pageable pageable) {
        if(!customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer with id " + customerId + " not found");
        }
        return orderRepository.findSummariesByCustomerId(customerId, pageable);
    }

    @Override
    public CustomerDetailView getCustomerDetails(long id) {
        return customerRepository.findWithContactInfoById(id)
                .orElseThrow(() -> new NotFoundException("Customer or ContactInfo not found for id: " + id));
    }

//    @Override
//    public Page<OrderWithProductCountView> listOrdersForCustomer(long customerId, Pageable pageable) {
//        // Optional but nice: fail fast if customer doesn't exist
//        if (!customerRepository.existsById(customerId)) {
//            throw new NotFoundException("Customer not found: " + customerId);
//        }
//        return orderRepository.findOrderWithProductCountByCustomerId(customerId, pageable);
//    }
}
