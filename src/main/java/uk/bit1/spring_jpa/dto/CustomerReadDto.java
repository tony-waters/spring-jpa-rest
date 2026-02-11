package uk.bit1.spring_jpa.dto;

public record CustomerReadDto(
        Long id,
        long version,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
