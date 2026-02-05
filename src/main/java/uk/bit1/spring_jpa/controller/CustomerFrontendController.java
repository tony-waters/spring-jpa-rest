package uk.bit1.spring_jpa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.bit1.spring_jpa.repository.projection.CustomerDetailView;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;
import uk.bit1.spring_jpa.service.CustomerServiceImpl;
import uk.bit1.spring_jpa.service.dto.CustomerDetailDto;

@RestController
@RequestMapping("/api/customers")
public class CustomerFrontendController {

    private final CustomerServiceImpl customerService;

    public CustomerFrontendController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Page<CustomerWithOrderCountView> listCustomers(Pageable pageable) {
        return customerService.listCustomers(pageable);
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

    @PostMapping("/new")
    public CustomerDetailDto addCustomer(CustomerDetailDto customerDetailDto) {
        return customerService.createCustomer(customerDetailDto);
    }
}
