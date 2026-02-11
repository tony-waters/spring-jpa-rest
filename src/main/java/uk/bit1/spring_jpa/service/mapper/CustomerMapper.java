package uk.bit1.spring_jpa.service.mapper;

import org.mapstruct.*;
import uk.bit1.spring_jpa.entity.ContactInfo;
import uk.bit1.spring_jpa.entity.CustomerUpdateDto;
import uk.bit1.spring_jpa.service.dto.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // Tell MapStruct how to create a Customer for CREATE
    @ObjectFactory
    default CustomerUpdateDto newCustomer(CustomerDetailCreateDto dto) {
        return new CustomerUpdateDto(dto.lastName(), dto.firstName());
    }

    @ObjectFactory
    default ContactInfo newContactInfo() {
        return new ContactInfo(null, null);
    }

    @Mapping(target = "contactInfo.email", source = "email")
    @Mapping(target = "contactInfo.phoneNumber", source = "phoneNumber")
    CustomerUpdateDto toEntity(CustomerCreateDto dto);

    @Mapping(target = "email", source = "contactInfo.email")
    @Mapping(target = "phoneNumber", source = "contactInfo.phoneNumber")
    @Mapping(target = "version", source = "version")
    CustomerReadDto toReadDto(CustomerUpdateDto customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromCreateDto(CustomerCreateDto dto, @MappingTarget CustomerUpdateDto customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true) // JPA manages it; client version is checked in service
    void updateEntityFromUpdateDto(uk.bit1.spring_jpa.service.dto.CustomerUpdateDto dto, @MappingTarget CustomerUpdateDto customer);
}
