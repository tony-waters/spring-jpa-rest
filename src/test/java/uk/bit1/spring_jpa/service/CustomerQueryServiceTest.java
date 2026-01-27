package uk.bit1.spring_jpa.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import uk.bit1.spring_jpa.service.impl.CustomerQueryServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CustomerQueryServiceImpl.class)
class CustomerQueryServiceTest {

    @Autowired
    CustomerQueryService customerQueryService;

    @Test
    void listCustomers_returnsPagedProjection() {
        var page = customerQueryService.listCustomers(PageRequest.of(0, 10));
        assertThat(page).isNotNull();
        // you can assert size/content depending on your seeded test data
    }
}
