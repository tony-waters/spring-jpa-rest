package uk.bit1.spring_jpa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.bit1.spring_jpa.repository.projection.CustomerDetailView;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;
import uk.bit1.spring_jpa.service.CustomerQueryService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController implements CustomerRestRequirements {

    private final CustomerQueryService customerQueryService;

    public CustomerController(CustomerQueryService customerQueryService) {
        this.customerQueryService = customerQueryService;
    }

    @GetMapping
    public Page<CustomerWithOrderCountView> listCustomers(Pageable pageable) {
        return customerQueryService.listCustomers(pageable);
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
    public void updateCustomerDetails(CustomerDetailView customerDetail) {

    }

    @Override
    public void deleteCustomer(Long id) {

    }

    @Override
    public CustomerDetailView addCustomer(CustomerDetailView customerDetail) {
        return null;
    }
}
