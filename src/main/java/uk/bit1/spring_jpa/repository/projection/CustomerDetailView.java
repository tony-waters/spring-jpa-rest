package uk.bit1.spring_jpa.repository.projection;

public interface CustomerDetailView {

    CustomerView getCustomer();
    String getEmail();
    String getPhone();

}
