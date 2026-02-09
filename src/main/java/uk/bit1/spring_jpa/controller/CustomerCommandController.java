package uk.bit1.spring_jpa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.bit1.spring_jpa.service.CustomerCommandService;
import uk.bit1.spring_jpa.service.dto.CustomerDetailCreateDto;
import uk.bit1.spring_jpa.service.dto.CustomerDetailDto;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerCommandController {

    private final CustomerCommandService customerCommandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDetailDto createCustomer(@Valid @RequestBody CustomerDetailCreateDto request) {
        return customerCommandService.createCustomer(request);
    }

    @PutMapping("/{id}")
    public CustomerDetailDto updateCustomer(
            @PathVariable long id,
            @Valid @RequestBody CustomerDetailDto request) {
        return customerCommandService.updateCustomerDetails(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable long id) {
        customerCommandService.deleteCustomerById(id);
    }
}
