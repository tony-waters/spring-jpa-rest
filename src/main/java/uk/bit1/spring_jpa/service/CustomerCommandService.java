package uk.bit1.spring_jpa.service;

import uk.bit1.spring_jpa.service.dto.*;

public interface CustomerCommandService {
//    CustomerDetailDto createCustomer(CustomerDetailCreateDto request);
//    CustomerDetailDto updateCustomerDetails(long id, CustomerDetailDto request);
    CustomerReadDto createCustomer(CustomerCreateDto dto);
    CustomerReadDto updateCustomer(long id, CustomerUpdateDto dto);
    void deleteCustomerById(long id);
}
