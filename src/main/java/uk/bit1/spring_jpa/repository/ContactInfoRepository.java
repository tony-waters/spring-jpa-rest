package uk.bit1.spring_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.bit1.spring_jpa.entity.ContactInfo;

import java.util.Optional;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {

    // For uniqueness checks before create/update (still keep DB constraint!)
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndCustomer_IdNot(String email, Long customerId);

    // Handy if you ever want to look up by email (login, lookup, etc.)
    Optional<ContactInfo> findByEmailIgnoreCase(String email);

    // If you want to enforce "one ContactInfo per Customer" at service level
    boolean existsByCustomerId(Long customerId);
}
