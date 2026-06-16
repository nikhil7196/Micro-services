package com.medi360.exception;

public class InvoiceNotFoundException extends Exception {


    private static final long serialVersionUID = 1L;

    public InvoiceNotFoundException() {
        super();
    }

    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
