package uk.bit1.spring_jpa.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CustomerQueryService.class)
class CustomerQueryServiceTest {

    @Autowired
    CustomerQueryService customerQueryService;

    @Test
    void listCustomers_returnsPagedProjection() {
        var page = customerQueryService.listCustomers(PageRequest.of(0, 10));
        assertThat(page).isNotNull();
        // can assert size/content depending on seeded test data
    }
}
