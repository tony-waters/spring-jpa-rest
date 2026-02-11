package uk.bit1.spring_jpa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import uk.bit1.spring_jpa.validation.EmailUnique;

public record CustomerCreateDto(
        @NotBlank(message = "First Name is required")
        @Size(min = 2, max = 50, message = "First Name must be 2-50 characters")
        String firstName,

        @NotBlank(message = "Last Name is required")
        @Size(min = 2, max = 50, message = "Last Name must be 2-50 characters")
        String lastName,

        @Email(message = "Email must be valid")
        @NotBlank(message = "Email is required")
        @EmailUnique
        String email,

        @NotBlank(message = "Phone Number is required")
        @Size(min = 11, max = 20, message = "Phone Number must be 11-20 characters")
        String phoneNumber
) {}
