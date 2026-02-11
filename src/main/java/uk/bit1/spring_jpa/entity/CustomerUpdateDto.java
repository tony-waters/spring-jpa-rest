package uk.bit1.spring_jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CustomerUpdateDto extends BaseEntity {

    @Valid
    @OneToOne(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private ContactInfo contactInfo; // = new ContactInfo(null, null);

    @Valid
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Order> orders = new HashSet<>();

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(length = 50, name = "last_name", nullable = false)
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(length = 50, name = "first_name", nullable = false)
    private String firstName;

//    protected Customer() {}

    public CustomerUpdateDto(String lastName, String firstName) {
        this.lastName= lastName;
        this.firstName = firstName;
    }

    public CustomerUpdateDto(String lastName, String firstName, String email, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        setContactInfo(new ContactInfo(email, phoneNumber));
    }

    public void addOrder(Order order) {
        if(order == null) return;
//        if(orders.add(order)) {
        order.setCustomer(this);
//        }
    }

    public void removeOrder(Order order) {
        if(order == null) return;
//        if(orders.remove(order)) {
        if(orders.contains(order)) {
            order.setCustomer(null);
        }
    }

    public void removeAllOrders() {
        // Iterating over a copy avoids ConcurrentModificationException
        for (Order order : new HashSet<>(orders)) {
            removeOrder(order);
        }
    }

    // Package-private helpers for Order to update this collection without recursion.
    void addOrderInternal(Order order) {
        orders.add(order);
    }

    void removeOrderInternal(Order order) {
        orders.remove(order);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        if (this.contactInfo == contactInfo) {
            return;
        };
        if (this.contactInfo != null) {
            this.contactInfo.setCustomer(null);
        }
        this.contactInfo = contactInfo;
        if (contactInfo != null) {
            contactInfo.setCustomer(this);
        }
    }

    public Set<Order> getOrders() {
        return java.util.Collections.unmodifiableSet(orders);
    }

    // no setOrders by design

    @Override
    public String toString() {
        return "Customer{id=" + getId() + ", firstName=" + firstName + ", lastName=" + lastName + "}";
    }

}
