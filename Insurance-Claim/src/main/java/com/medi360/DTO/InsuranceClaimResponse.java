package com.medi360.DTO;

public class InsuranceClaimResponse {

    private Object insuranceClaim; 
    private int statusCode;
    private String message;

    public Object getInsuranceClaim() { return insuranceClaim; }
    public void setInsuranceClaim(Object insuranceClaim) { this.insuranceClaim = insuranceClaim; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}