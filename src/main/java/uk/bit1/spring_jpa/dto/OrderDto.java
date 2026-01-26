package uk.bit1.spring_jpa.dto;

import java.util.List;

public record OrderDto(Long id, String description, Boolean fulfilled, CustomerDto customer, List<ProductDto> products) {
}
