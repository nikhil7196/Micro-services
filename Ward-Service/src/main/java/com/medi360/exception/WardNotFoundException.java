package com.medi360.exception;


public class WardNotFoundException extends Exception {


    private static final long serialVersionUID = 1L;

    public WardNotFoundException() {
        super();
    }

    public WardNotFoundException(String message) {
        super(message);
    }

    public WardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}


