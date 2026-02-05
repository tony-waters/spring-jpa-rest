package uk.bit1.spring_jpa.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerDetailCreateDto {

    @NotBlank(message = "First Name is required")
    @Size(min = 2, max = 50, message = "First Name must be 2-50 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(min = 2, max = 50, message = "Last Name must be 2-50 characters")
    private String lastName;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone Number is required")
    @Size(min = 11, max = 20, message = "Phone Number must be 11-20 characters")
    private String phoneNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
