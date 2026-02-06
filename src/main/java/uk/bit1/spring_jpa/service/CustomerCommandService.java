package uk.bit1.spring_jpa.service;

import uk.bit1.spring_jpa.service.dto.CustomerDetailCreateDto;
import uk.bit1.spring_jpa.service.dto.CustomerDetailDto;

public interface CustomerCommandService {
    CustomerDetailDto createCustomer(CustomerDetailCreateDto request);
    CustomerDetailDto updateCustomerDetails(long id, CustomerDetailDto request);
    void deleteCustomerById(long id);
}
