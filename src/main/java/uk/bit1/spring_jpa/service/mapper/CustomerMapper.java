package uk.bit1.spring_jpa.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.service.dto.CustomerDetailCreateDto;
import uk.bit1.spring_jpa.service.dto.CustomerDetailDto;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerDetailCreateDto dto);

    CustomerDetailDto toDetailDto(Customer customer);

    void updateEntityFromDto(CustomerDetailDto dto, @MappingTarget Customer entity);
}

