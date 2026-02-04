package uk.bit1.spring_jpa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.bit1.spring_jpa.repository.projection.CustomerDetailView;
import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;
import uk.bit1.spring_jpa.repository.projection.OrderWithProductCountView;

// interface needed by current front end
public interface CustomerRestRequirements {

    Page<CustomerWithOrderCountView> listCustomers(Pageable pageable);

    Page<OrderWithProductCountView> listOrdersForCustomer(Pageable pageable);

    CustomerDetailView getCustomerDetails(Long id);

    void updateCustomerDetails(CustomerDetailView customerDetail);

    void deleteCustomer(Long id);

    CustomerDetailView addCustomer(CustomerDetailView customerDetail);

}
