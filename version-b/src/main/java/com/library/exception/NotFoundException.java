package com.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Version B - AI-Assisted Approach | Layer: EXCEPTION
 * Thrown when a book or an active borrow record cannot be found.
 * The @ResponseStatus lets Spring translate it to HTTP 404 automatically -
 * a cleaner alternative to Version A's manual status handling (AI suggestion).
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
