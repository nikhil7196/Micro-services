package com.medi360.DTO;

public class InvoiceResponseDTO {

    private Object invoice; 
    private int statusCode;
    private String message;

    public Object getInvoice() { return invoice; }
    public void setInvoice(Object invoice) { this.invoice = invoice; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}