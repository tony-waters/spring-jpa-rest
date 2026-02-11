package uk.bit1.spring_jpa.service;

import uk.bit1.spring_jpa.dto.CustomerCreateDto;
import uk.bit1.spring_jpa.dto.CustomerReadDto;
import uk.bit1.spring_jpa.dto.CustomerUpdateDto;

public interface CustomerCommandService {
    CustomerReadDto createCustomer(CustomerCreateDto dto);
    CustomerReadDto updateCustomer(long id, CustomerUpdateDto dto);
    void deleteCustomerById(long id);
}
