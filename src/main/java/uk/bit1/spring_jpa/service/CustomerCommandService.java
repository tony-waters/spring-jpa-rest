package uk.bit1.spring_jpa.service;

import uk.bit1.spring_jpa.service.dto.CustomerDetailCreateDto;
import uk.bit1.spring_jpa.service.dto.CustomerDetailUpdateDto;

public interface CustomerCommandService {
    CustomerDetailCreateDto createCustomer(CustomerDetailCreateDto request);
    CustomerDetailUpdateDto updateCustomerDetails(long id, CustomerDetailUpdateDto request);
    void deleteCustomerById(long id);
}
