package uk.bit1.spring_jpa.service.mapper;

import org.mapstruct.Mapper;
import uk.bit1.spring_jpa.dto.CustomerDto;
import uk.bit1.spring_jpa.entity.Customer;

import java.util.List;

@Mapper(componentModel = "spring", uses = { OrderMapper.class })
public interface CustomerMapper {

    Customer toEntity(CustomerDto dto);

    CustomerDto toDto(Customer entity);

    List<CustomerDto> map(List<Customer> customers);
}
