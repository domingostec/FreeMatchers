package org.example.freematchers.exceptions;

public class LackRequiredHoursException extends RuntimeException {
    public LackRequiredHoursException(String message) {
        super(message);
    }
}
