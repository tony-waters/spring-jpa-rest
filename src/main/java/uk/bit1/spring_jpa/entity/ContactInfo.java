package uk.bit1.spring_jpa.entity;

import jakarta.persistence.*;

@Entity
public class ContactInfo extends BaseEntity {

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Customer customer;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

    protected ContactInfo() {}

    public ContactInfo(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactInfo other)) return false;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
