package uk.bit1.spring_jpa.service;

import uk.bit1.spring_jpa.service.dto.CustomerDetailDto;

public interface CustomerCommandService {
    CustomerDetailDto createCustomer(CustomerDetailDto request);
    CustomerDetailDto updateCustomerDetails(long id, CustomerDetailDto request);
    void deleteCustomerById(long id);
}
