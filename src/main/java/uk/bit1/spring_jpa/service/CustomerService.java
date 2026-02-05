package uk.bit1.spring_jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.bit1.spring_jpa.service.dto.CustomerDetailDto;
import uk.bit1.spring_jpa.repository.projection.CustomerDetailView;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;

// interface needed by current front end
public interface CustomerService {

    Page<CustomerWithOrderCountView> listCustomers(Pageable pageable);

    Page<OrderWithProductCountView> listOrdersForCustomer(Pageable pageable);

    CustomerDetailView getCustomerDetails(Long id);

    CustomerDetailView updateCustomerDetails(CustomerDetailView customerDetail);

    void deleteCustomerById(Long id);

    CustomerDetailDto createCustomer(CustomerDetailDto customerDetail);

}
