package com.medi360.exception;

public class DoctorNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public DoctorNotFoundException() {
        super();
    }

    public DoctorNotFoundException(String message) {
        super(message);
    }

    public DoctorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}