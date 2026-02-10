package uk.bit1.spring_jpa.service;

import uk.bit1.spring_jpa.service.dto.*;

public interface CustomerCommandService {
    CustomerReadDto createCustomer(CustomerCreateDto dto);
    CustomerReadDto updateCustomer(long id, CustomerUpdateDto dto);
    void deleteCustomerById(long id);
}
