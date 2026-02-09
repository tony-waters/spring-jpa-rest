package uk.bit1.spring_jpa.repository.projection;

public interface CustomerDetailView {
    Long getId();
    String getFirstName();
    String getLastName();
    ContactInfoView getContactInfo();

    interface ContactInfoView {
        String getEmail();
        String getPhoneNumber();
    }
}

