package uk.bit1.spring_jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactInfo extends BaseEntity {

    @Valid
    @OneToOne(optional = true)
    @JoinColumn(name="customer_id", unique=true, nullable = true)
    private CustomerUpdateDto customer;

    @NotBlank
    @Email
    @Column(length = 320, unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(min = 11, max = 20)
    @Column(length = 20, nullable = false)
    private String phoneNumber;

    public ContactInfo(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public void setPhoneNumber(String phone) {
        this.phoneNumber = phone;
    }

    public CustomerUpdateDto getCustomer() {
        return customer;
    }

    protected void setCustomer(CustomerUpdateDto customer) {
        this.customer = customer;
    }

}
