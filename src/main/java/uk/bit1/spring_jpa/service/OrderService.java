package uk.bit1.spring_jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.entity.Order;
import uk.bit1.spring_jpa.entity.Product;
import uk.bit1.spring_jpa.repository.CustomerRepository;
import uk.bit1.spring_jpa.repository.OrderRepository;
import uk.bit1.spring_jpa.repository.ProductRepository;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCount;
import uk.bit1.spring_jpa.service.exception.NotFoundException;

import java.util.*;

@Service
public class OrderService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(CustomerRepository customerRepository,
                        OrderRepository orderRepository,
                        ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(Long customerId, String description) {
        Customer c = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer", customerId));

        Order o = new Order(description);
        c.addOrder(o); // cascade persists via Customer
        // saving customer not required if managed; but safe if customer might be detached
        customerRepository.save(c);
        return o;
    }

    @Transactional
    public void addProductToOrder(Long orderId, Long productId) {
        Order o = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order", orderId));

        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product", productId));

        o.addProduct(p);
        // o is managed; flushed on commit
    }

    @Transactional(readOnly = true)
    public Page<OrderWithProductCount> listOrdersWithProductCount(Long customerId, Pageable pageable) {
        return orderRepository.findOrdersAndProductCountByCustomerId(customerId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Order> listOrdersWithProducts(Long customerId, Pageable pageable) {
        Page<Long> idPage = orderRepository.findOrderIdsByCustomerId(customerId, pageable);
        if (idPage.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, idPage.getTotalElements());
        }

        List<Long> ids = idPage.getContent();
        List<Order> fetched = orderRepository.findOrdersWithProductsByIdIn(ids);

        // Ensure same order as the ids page
        Map<Long, Integer> pos = new HashMap<>(ids.size());
        for (int i = 0; i < ids.size(); i++) pos.put(ids.get(i), i);
        fetched.sort(Comparator.comparingInt(o -> pos.getOrDefault(o.getId(), Integer.MAX_VALUE)));

        return new PageImpl<>(fetched, pageable, idPage.getTotalElements());
    }
}
