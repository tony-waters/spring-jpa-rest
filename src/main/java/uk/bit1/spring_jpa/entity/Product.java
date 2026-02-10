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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Valid
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    @NotBlank
    @Size(min = 2, max = 255)
    @Column(length = 255, nullable = false)
    private String name;

    @NotBlank
    @Size(min = 2, max = 255)
    @Column(length = 255, nullable = false)
    private String description;

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected void addOrder(Order order) {
        if (order == null) return;
        orders.add(order);
    }

    protected void removeOrder(Order order) {
        if(order == null) return;
        orders.remove(order);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Order> getOrders() {
        // stop modification via the Collection interface
        return java.util.Collections.unmodifiableSet(orders);
    }

    // no setOrders() by design

}
