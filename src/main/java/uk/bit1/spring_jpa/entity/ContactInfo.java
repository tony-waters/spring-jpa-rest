package uk.bit1.spring_jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactInfo extends BaseEntity {

//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "id")
    @OneToOne(optional=false)
    @JoinColumn(name="customer_id", unique=true)
    private Customer customer;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

//    protected ContactInfo() {}
////    public ContactInfo() {}
//    // public factory method
////    public static ContactInfo create() {
////        return new ContactInfo("", "");
////    }

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
