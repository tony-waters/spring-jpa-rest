package uk.bit1.spring_jpa.service.dto;

import java.util.List;

public record OrderDto(Long id, String description, Boolean fulfilled, CustomerDetailDto customer, List<ProductDto> products) {
}
