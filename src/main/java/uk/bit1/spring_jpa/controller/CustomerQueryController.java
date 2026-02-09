package uk.bit1.spring_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uk.bit1.spring_jpa.repository.projection.CustomerDetailView;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;
import uk.bit1.spring_jpa.service.CustomerQueryService;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerQueryController {

    private final CustomerQueryService customerQueryService;

    @GetMapping
    public Page<CustomerWithOrderCountView> listCustomers(
            @PageableDefault(size = 20) Pageable pageable) {
        return customerQueryService.listCustomers(pageable);
    }

    @GetMapping("/{id}")
    public CustomerDetailView getCustomerDetails(@PathVariable long id) {
        return customerQueryService.getCustomerDetails(id);
    }

    @GetMapping("/{id}/orders")
    public Page<OrderWithProductCountView> listOrdersForCustomer(
            @PathVariable long id,
            @PageableDefault(size = 20) Pageable pageable) {
        return customerQueryService.listOrdersForCustomer(id, pageable);
    }
}

