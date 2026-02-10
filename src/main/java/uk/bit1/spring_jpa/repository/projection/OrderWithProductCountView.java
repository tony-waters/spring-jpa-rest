package uk.bit1.spring_jpa.repository.projection;

public interface OrderWithProductCountView {
    Long getId();
    String getDescription();
    boolean isFulfilled();
    long getProductCount();
}
