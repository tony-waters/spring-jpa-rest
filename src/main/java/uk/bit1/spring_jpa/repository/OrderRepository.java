package uk.bit1.spring_jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uk.bit1.spring_jpa.entity.Order;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        select
          o.id as id,
          o.description as description,
          o.fulfilled as fulfilled,
          count(p) as productCount
        from Order o
        left join o.products p
        where o.customer.id = :customerId
        group by o.id, o.description, o.fulfilled
        """)
    Page<OrderWithProductCountView> findSummariesByCustomerId(
            @Param("customerId") long customerId,
            Pageable pageable
    );

    // Useful for authorization / ownership checks
    boolean existsByIdAndCustomer_Id(long orderId, long customerId);
}
