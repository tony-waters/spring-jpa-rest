package uk.bit1.spring_jpa.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uk.bit1.spring_jpa.dto.CustomerCreateDto;
import uk.bit1.spring_jpa.dto.CustomerReadDto;
import uk.bit1.spring_jpa.dto.CustomerUpdateDto;
import uk.bit1.spring_jpa.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerReadDto toReadDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateFromCreate(CustomerCreateDto dto, @MappingTarget Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateFromUpdate(CustomerUpdateDto dto, @MappingTarget Customer customer);
}
