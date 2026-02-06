package uk.bit1.spring_jpa.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerDetailDto {

    private final Long id;

    private final Long version;

    @NotBlank(message = "First Name is required")
    @Size(min = 2, max = 50, message = "First Name must be 2-50 characters")
    private final String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(min = 2, max = 50, message = "Last Name must be 2-50 characters")
    private final String lastName;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private final String email;

    @NotBlank(message = "Phone Number is required")
    @Size(min = 11, max = 20, message = "Phone Number must be 11-20 characters")
    private final String phoneNumber;

    public CustomerDetailDto(Long id, Long version, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.version = version;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getFirstName() {
        return firstName;
    }

//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }

    public String getLastName() {
        return lastName;
    }

//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }

    public String getEmail() {
        return email;
    }

//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }

    public Long getVersion() {
        return version;
    }

//    public void setVersion(Long version) {
//        this.version = version;
//    }
}
