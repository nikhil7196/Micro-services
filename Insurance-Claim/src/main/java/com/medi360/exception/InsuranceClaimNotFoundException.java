package com.medi360.exception;

public class InsuranceClaimNotFoundException extends Exception {


    private static final long serialVersionUID = 1L;

    public InsuranceClaimNotFoundException() {
        super();
    }

    public InsuranceClaimNotFoundException(String message) {
        super(message);
    }
}