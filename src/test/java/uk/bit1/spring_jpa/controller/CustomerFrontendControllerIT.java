package uk.bit1.spring_jpa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import uk.bit1.spring_jpa.entity.Customer;
import uk.bit1.spring_jpa.repository.CustomerRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CustomerFrontendControllerIT {

    @Autowired MockMvc mockMvc;
    @Autowired CustomerRepository customerRepository;

    @Test
    void getCustomers_returnsProjectionFields() throws Exception {
        // Arrange
        customerRepository.save(new Customer("Doe", "Jane"));

        // Act + Assert
        mockMvc.perform(get("/api/customers")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())

                // Page shape
                .andExpect(jsonPath("$.content", is(notNullValue())))
                .andExpect(jsonPath("$.content", is(not(empty()))))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.size", is(10)))
                .andExpect(jsonPath("$.totalElements", is(greaterThanOrEqualTo(1))))

                // Projection fields (CustomerWithOrderCount)
                .andExpect(jsonPath("$.content[0].customerId", is(notNullValue())))
                .andExpect(jsonPath("$.content[0].firstName", is("Jane")))
                .andExpect(jsonPath("$.content[0].lastName", is("Doe")))
                .andExpect(jsonPath("$.content[0].orderCount", is(greaterThanOrEqualTo(0))));
    }
}
