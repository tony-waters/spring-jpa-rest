package uk.bit1.spring_jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.bit1.spring_jpa.entity.CustomerUpdateDto;
import uk.bit1.spring_jpa.repository.projection.CustomerDetailView;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerUpdateDto, Long> {

    Page<CustomerUpdateDto> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    // Fetch graphs (use when you know you need relationships)
//    @EntityGraph(attributePaths = {"orders"})
//    Optional<Customer> findWithOrdersById(Long id);

    // Loads Customer and ContactInfo
    @EntityGraph(attributePaths = {"contactInfo"})
    Optional<CustomerDetailView> findWithContactInfoById(Long id);

//    @EntityGraph(attributePaths = {"orders", "orders.products"})
//    Optional<Customer> findWithOrdersAndProductsById(Long id);

    @Query(
            value = """
                select c.id as customerId,
                       c.firstName as firstName,
                       c.lastName as lastName,
                       count(o.id) as orderCount
                from CustomerUpdateDto c
                left join c.orders o
                group by c.id, c.firstName, c.lastName
                order by c.lastName, c.id
            """,
            countQuery = "select count(c) from CustomerUpdateDto c"
    )
    Page<CustomerWithOrderCountView> findAllCustomerWithOrderCount(Pageable pageable);

//    @Query("""
//       select c
//       from Customer c
//       left join fetch c.orders o
//       left join fetch o.products p
//       where c.id = :id
//       """)
//    Optional<CustomerDetailView> findCustomerDetailViewById(@Param("id") long id);

//    Optional<CustomerDetailView> findByContactInfoAn

//    Optional<CustomerDetailView> findByContactInfo_Customer_Id(Long contactInfoCustomerId);
}