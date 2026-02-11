package uk.bit1.spring_jpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrphanRemovalJpaTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void orphanRemoval_removingOrderFromCustomer_deletesOrderRow() {
        // create graph
        Customer customer = new Customer("Smith", "Emily");
        Order order = new Order("Orphan me");
        customer.addOrder(order);

        // persist only the root; cascade persists the order
        entityManager.persist(customer);
        entityManager.flush();

        Long orderId = order.getId();
        assertThat(orderId).isNotNull();

        // IMPORTANT: operate on managed entities
        Customer managedCustomer = entityManager.find(Customer.class, customer.getId());
        Order managedOrder = entityManager.find(Order.class, orderId);

        // remove from the parent's collection (entity method maintains both sides)
        managedCustomer.removeOrder(managedOrder);

        entityManager.flush();
        entityManager.clear();

        // If orphanRemoval works, the Order row is gone
        Order deleted = entityManager.find(Order.class, orderId);
        assertThat(deleted).isNull();
    }

    @Test
    void orphanRemoval_removingContactInfoFromCustomer_deletesContactInfoRow() {
        Customer customer = new Customer("Waters", "Tony");
        ContactInfo contactInfo = new ContactInfo("tony@example.com", "07123456789");
        customer.setContactInfo(contactInfo); // protected; test is in same package

        entityManager.persist(customer);
        entityManager.flush();

        Long contactId = contactInfo.getId();
        assertThat(contactId).isNotNull();

        Customer managedCustomer = entityManager.find(Customer.class, customer.getId());
        managedCustomer.setContactInfo(null);

        entityManager.flush();
        entityManager.clear();

        ContactInfo deleted = entityManager.find(ContactInfo.class, contactId);
        assertThat(deleted).isNull();
    }

    @Test
    void deletingOrder_removesJoinTableRows_order_products() {
        // Products have NO cascade from Order in your mapping, so persist them first
        Product product1 = new Product("Tea", "Yorkshire");
        Product product2 = new Product("Biscuits", "Hobnobs");
        entityManager.persist(product1);
        entityManager.persist(product2);

        Customer customer = new Customer("Brown", "Esther");
        Order order = new Order("Shopping");
        customer.addOrder(order);

        // Owning side adds products -> creates join table rows
        order.addProduct(product1);
        order.addProduct(product2);

        // Persist root; cascade persists the order
        entityManager.persist(customer);
        entityManager.flush();

        Long orderId = order.getId();
        assertThat(orderId).isNotNull();

        // Confirm join table rows exist
        long before = ((Number) entityManager.createNativeQuery(
                        "select count(*) from order_products where order_id = ?")
                .setParameter(1, orderId)
                .getSingleResult()).longValue();

        assertThat(before).isEqualTo(2L);

        // Trigger deletion of Order via orphanRemoval by removing it from the Customer aggregate
        Customer managedCustomer = entityManager.find(Customer.class, customer.getId());
        Order managedOrder = entityManager.find(Order.class, orderId);
        managedCustomer.removeOrder(managedOrder);

        entityManager.flush();
        entityManager.clear();

        // Order row should be deleted (orphan removal)
        assertThat(entityManager.find(Order.class, orderId)).isNull();

        // Join table rows should also be gone
        long after = ((Number) entityManager.createNativeQuery(
                        "select count(*) from order_products where order_id = ?")
                .setParameter(1, orderId)
                .getSingleResult()).longValue();

        assertThat(after).isEqualTo(0L);
    }

    @Test
    void deletingProduct_removesJoinTableRows_order_products() {
        // Create and persist products first (no cascade from Order->Product)
        Product product1 = new Product("Tea", "Yorkshire");
        Product product2 = new Product("Biscuits", "Hobnobs");
        entityManager.persist(product1);
        entityManager.persist(product2);

        Customer customer = new Customer("Brown", "Esther");
        Order order = new Order("Shopping");
        customer.addOrder(order);

        order.addProduct(product1);
        order.addProduct(product2);

        entityManager.persist(customer);
        entityManager.flush();

        Long orderId = order.getId();
        Long productId = product1.getId();
        assertThat(orderId).isNotNull();
        assertThat(productId).isNotNull();

        // Sanity: join rows exist for product1
        long before = ((Number) entityManager.createNativeQuery(
                        "select count(*) from order_products where product_id = ?")
                .setParameter(1, productId)
                .getSingleResult()).longValue();
        assertThat(before).isEqualTo(1L);

        // Work with managed entities
        Order managedOrder = entityManager.find(Order.class, orderId);
        Product managedProduct = entityManager.find(Product.class, productId);

        // Provider-neutral: break association from owning side first
        managedOrder.removeProduct(managedProduct);

        entityManager.flush();

        // Now delete the product
        entityManager.remove(managedProduct);

        entityManager.flush();
        entityManager.clear();

        // Product row should be deleted
        assertThat(entityManager.find(Product.class, productId)).isNull();

        // Join table rows referencing product_id should be gone
        long after = ((Number) entityManager.createNativeQuery(
                        "select count(*) from order_products where product_id = ?")
                .setParameter(1, productId)
                .getSingleResult()).longValue();
        assertThat(after).isEqualTo(0L);
    }

}
