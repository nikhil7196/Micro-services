package com.medi360.exception;


public class PatientNotFoundException extends Exception {


    private static final long serialVersionUID = 1L;

    public PatientNotFoundException() {
        super();
    }

    public PatientNotFoundException(String message) {
        super(message);
    }

    public PatientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}


