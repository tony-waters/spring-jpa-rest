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
    private Customer customer;

    @NotBlank
    @Email
    @Column(length = 320, nullable = false, unique = true)
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

    public Customer getCustomer() {
        return customer;
    }

    protected void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
