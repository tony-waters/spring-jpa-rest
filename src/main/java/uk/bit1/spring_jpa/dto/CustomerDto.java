package uk.bit1.spring_jpa.dto;

import java.util.List;

public record CustomerDto(Long id, String lastName, String firstName, List<OrderDto> orders) {
}
