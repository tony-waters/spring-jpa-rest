package uk.bit1.spring_jpa.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCount;
import uk.bit1.spring_jpa.service.CustomerQueryService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerQueryService customerQueryService;

    public CustomerController(CustomerQueryService customerQueryService) {
        this.customerQueryService = customerQueryService;
    }

    @GetMapping
    public Page<CustomerWithOrderCount> listCustomers(Pageable pageable) {
        return customerQueryService.listCustomers(pageable);
    }
}
