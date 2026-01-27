package uk.bit1.spring_jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCount;

public interface CustomerQueryService {
    Page<CustomerWithOrderCount> listCustomers(Pageable pageable);
}
