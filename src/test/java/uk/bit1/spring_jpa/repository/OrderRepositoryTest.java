package uk.bit1.spring_jpa.repository;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.entity.Order;
import uk.bit1.spring_jpa.entity.Product;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.generate_statistics=true"
})
@DataJpaTest
class OrderRepositoryTest {

    @Autowired OrderRepository orderRepository;
    @Autowired TestEntityManager entityManager;
    @Autowired EntityManagerFactory entityManagerFactory;

    private Statistics getStatistics() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Statistics statistics = sessionFactory.getStatistics();
        statistics.clear();
        return statistics;
    }

    @Test
    void findOrdersAndProductCountByCustomerId_countsDistinctProducts() {
        Product product1 = new Product("Tea", "Yorkshire");
        Product product2 = new Product("Biscuits", "Hobnobs");
        entityManager.persist(product1);
        entityManager.persist(product2);

        Customer customer = new Customer("Brown", "Esther");
        Order order1 = new Order("Order #1");
        Order order2 = new Order("Order #2");
        customer.addOrder(order1);
        customer.addOrder(order2);

        // o1 has 2 products, o2 has 1
        order1.addProduct(product1);
        order1.addProduct(product2);
        order2.addProduct(product1);

        entityManager.persist(customer);
        entityManager.flush();
        entityManager.clear();

        Statistics statistics = getStatistics();
        var page = orderRepository.findOrdersAndProductCountByCustomerId(customer.getId(), PageRequest.of(0, 10));
        assertThat(statistics.getPrepareStatementCount()).isBetween(1L, 2L); // check SELECT count
        assertThat(page.getTotalElements()).isEqualTo(2);

        List<OrderWithProductCountView> rows = page.getContent();
        assertThat(rows).hasSize(2);

        // ordered by o.id, so just assert counts appear
        assertThat(rows).extracting(OrderWithProductCountView::getProductCount)
                .containsExactlyInAnyOrder(2L, 1L);
    }

    @Test
    void findOrderIdsByCustomerId_pagesIdsInOrder() {
        Customer customer = new Customer("Jones", "Belinda");
        Order order1 = new Order("Order #1");
        Order order2 = new Order("Order #2");
        Order order3 = new Order("Order #3");
        customer.addOrder(order1);
        customer.addOrder(order2);
        customer.addOrder(order3);

        entityManager.persist(customer);
        entityManager.flush();
        entityManager.clear();

        Statistics statistics = getStatistics();
        var page1 = orderRepository.findOrderIdsByCustomerId(customer.getId(), PageRequest.of(0, 2));
        assertThat(statistics.getPrepareStatementCount()).isBetween(1L, 2L); // check SELECT count
        statistics = getStatistics();
        var page2 = orderRepository.findOrderIdsByCustomerId(customer.getId(), PageRequest.of(1, 2));
        assertThat(statistics.getPrepareStatementCount()).isBetween(1L, 2L); // check SELECT count

        assertThat(page1.getContent()).hasSize(2);
        assertThat(page2.getContent()).hasSize(1);
        assertThat(page1.getTotalElements()).isEqualTo(3);
    }

    @Test
    void findOrdersWithProductsByIdIn_fetchesProductsForGivenIds() {
        Product product1 = new Product("Tea", "Yorkshire");
        Product product2 = new Product("Biscuits", "Hobnobs");
        entityManager.persist(product1);
        entityManager.persist(product2);

        Customer customer = new Customer("Brown", "Esther");
        Order order1 = new Order("Order #1");
        Order order2 = new Order("Order #2");
        customer.addOrder(order1);
        customer.addOrder(order2);

        order1.addProduct(product1);
        order1.addProduct(product2);
        order2.addProduct(product1);

        entityManager.persist(customer);
        entityManager.flush();
        entityManager.clear();

        // Load ids (simulate step 1)
        Statistics statistics = getStatistics();
        var idPage = orderRepository.findOrderIdsByCustomerId(customer.getId(), PageRequest.of(0, 10));
        assertThat(statistics.getPrepareStatementCount()).isBetween(1L, 2L); // check SELECT count
        List<Long> ids = idPage.getContent();

        // Step 2
        statistics = getStatistics();
        List<Order> fetched = orderRepository.findOrdersWithProductsByIdIn(ids);
        assertThat(statistics.getPrepareStatementCount()).isBetween(1L, 2L); // check SELECT count

        assertThat(fetched).hasSize(2);
        assertThat(fetched)
                .allSatisfy(o -> assertThat(o.getProducts()).isNotEmpty());

        // Important: ordering by IN isn't guaranteed; you can either:
        // - reorder in service, or
        // - add ORDER BY o.id in the fetch query (recommended)
        assertThat(fetched).extracting(Order::getId).containsExactlyInAnyOrderElementsOf(ids);
    }
}
