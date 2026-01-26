package uk.bit1.spring_jpa.service;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String type, Long id) {
        super(type + " not found: " + id);
    }
}