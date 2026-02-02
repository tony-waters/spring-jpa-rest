package uk.bit1.spring_jpa.dto.projection;

import uk.bit1.spring_jpa.repository.projection.CustomerWithOrderCountView;

// is this the right way to make a DTO from a projection?
public class CustomerWithOrderCountViewDto implements CustomerWithOrderCountView {
    @Override
    public Long getCustomerId() {
        return 0L;
    }

    @Override
    public String getFirstName() {
        return "";
    }

    @Override
    public String getLastName() {
        return "";
    }

    @Override
    public long getOrderCount() {
        return 0;
    }
}
