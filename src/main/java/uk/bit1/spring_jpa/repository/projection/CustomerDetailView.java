package uk.bit1.spring_jpa.repository.projection;

public interface CustomerDetailView {

    CustomerWithOrderCountView getCustomer();

    String getEmail();
    String getPhone();

}
