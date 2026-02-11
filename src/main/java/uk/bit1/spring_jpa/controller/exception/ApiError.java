package uk.bit1.spring_jpa.controller.exception;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        int status,
        String error,
        String message,
        Map<String, String> fieldErrors,
        Instant timestamp
) {}
