package uk.bit1.spring_jpa.repository.projection;

public interface CustomerView {
    Long getId();
    String getLastName();
    String getFirstName();
}
