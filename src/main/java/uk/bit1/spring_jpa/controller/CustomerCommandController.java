package uk.bit1.spring_jpa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.bit1.spring_jpa.dto.CustomerCreateDto;
import uk.bit1.spring_jpa.dto.CustomerReadDto;
import uk.bit1.spring_jpa.dto.CustomerUpdateDto;
import uk.bit1.spring_jpa.service.CustomerCommandService;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerCommandController {

    private final CustomerCommandService customerCommandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    public CustomerDetailDto createCustomer(@Valid @RequestBody CustomerDetailCreateDto request) {
//        return customerCommandService.createCustomer(request);
//    }
    public CustomerReadDto createCustomer(@Valid @RequestBody CustomerCreateDto request) {
        return customerCommandService.createCustomer(request);
    }

    @PutMapping("/{id}")
    public CustomerReadDto updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerUpdateDto request) {
        return customerCommandService.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable long id) {
        customerCommandService.deleteCustomerById(id);
    }
}
