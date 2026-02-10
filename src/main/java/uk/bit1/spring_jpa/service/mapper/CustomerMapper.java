package uk.bit1.spring_jpa.service.mapper;

import org.mapstruct.*;
import uk.bit1.spring_jpa.entity.ContactInfo;
import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.service.dto.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // Tell MapStruct how to create a Customer for CREATE
    @ObjectFactory
    default Customer newCustomer(CustomerDetailCreateDto dto) {
        return new Customer(dto.lastName(), dto.firstName());
    }

    @ObjectFactory
    default ContactInfo newContactInfo() {
        return new ContactInfo(null, null);
    }

    @Mapping(target = "contactInfo.email", source = "email")
    @Mapping(target = "contactInfo.phoneNumber", source = "phoneNumber")
    Customer toEntity(CustomerCreateDto dto);

    @Mapping(target = "email", source = "contactInfo.email")
    @Mapping(target = "phoneNumber", source = "contactInfo.phoneNumber")
    @Mapping(target = "version", source = "version")
    CustomerReadDto toReadDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromCreateDto(CustomerCreateDto dto, @MappingTarget Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true) // JPA manages it; client version is checked in service
    void updateEntityFromUpdateDto(CustomerUpdateDto dto, @MappingTarget Customer customer);
}
