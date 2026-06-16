package com.medi360.exception;


public class BedNotFoundException extends Exception {


    private static final long serialVersionUID = 1L;

    public BedNotFoundException() {
        super();
    }

    public BedNotFoundException(String message) {
        super(message);
    }

    public BedNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}


