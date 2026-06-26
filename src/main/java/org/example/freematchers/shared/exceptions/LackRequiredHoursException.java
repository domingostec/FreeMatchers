package org.example.freematchers.shared.exceptions;

public class LackRequiredHoursException extends RuntimeException {
    public LackRequiredHoursException(String message) {
        super(message);
    }
}
