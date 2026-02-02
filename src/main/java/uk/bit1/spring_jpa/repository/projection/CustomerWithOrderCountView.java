package uk.bit1.spring_jpa.repository.projection;

public interface CustomerWithOrderCountView {
    Long getCustomerId();
    String getFirstName();
    String getLastName();
    long getOrderCount();
}
