package uk.bit1.spring_jpa.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.bit1.spring_jpa.dto.OrderDto;
import uk.bit1.spring_jpa.entity.Order;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface OrderMapper {

//    // Entity → DTO
//    @Mapping(source = "customer.id", target = "customer.id")
//    OrderDto toDto(Order entity);
//
//    // DTO → Entity
//    // customer is set in the service layer
//    @Mapping(target = "customer", ignore = true)
//    Order toEntity(OrderDto dto);
//
//    List<OrderDto> map(List<Order> customerOrders);
}
