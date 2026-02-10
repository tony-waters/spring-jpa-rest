package uk.bit1.spring_jpa.service.mapper;

import org.mapstruct.*;
import uk.bit1.spring_jpa.entity.ContactInfo;
import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.service.dto.CustomerDetailCreateDto;
import uk.bit1.spring_jpa.service.dto.CustomerDetailDto;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // Tell MapStruct how to create a Customer for CREATE
    @ObjectFactory
    default Customer newCustomer(CustomerDetailCreateDto dto) {
        // Your constructor is (lastName, firstName)
        return new Customer(dto.lastName(), dto.firstName());
    }

    @ObjectFactory
    default ContactInfo newContactInfo() {
        return new ContactInfo(null, null);
    }

    @Mapping(target = "contactInfo.email", source = "email")
    @Mapping(target = "contactInfo.phoneNumber", source = "phoneNumber")
    Customer toEntity(CustomerDetailCreateDto dto);

    @Mapping(target = "email", source = "contactInfo.email")
    @Mapping(target = "phoneNumber", source = "contactInfo.phoneNumber")
    @Mapping(target = "version", source = "version")
    CustomerDetailDto toDetailDto(Customer customer);

    @Mapping(target = "contactInfo.email", source = "email")
    @Mapping(target = "contactInfo.phoneNumber", source = "phoneNumber")
    void updateEntityFromDto(CustomerDetailDto dto, @MappingTarget Customer entity);
}
