package com.medi360.DTO;

import java.time.LocalDate;

public class InvoiceWithPatientDTO {

    private int invoiceId;
    private PatientDTO patient;  
    private double amount;
    private LocalDate invoiceDate;
    private String paymentStatus;
    private String paymentMode;
    private double adjustmentAmount;
    private String refundStatus;

    public int getInvoiceId() { return invoiceId; }
    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }

    public PatientDTO getPatient() { return patient; }
    public void setPatient(PatientDTO patient) { this.patient = patient; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public double getAdjustmentAmount() { return adjustmentAmount; }
    public void setAdjustmentAmount(double adjustmentAmount) { this.adjustmentAmount = adjustmentAmount; }

    public String getRefundStatus() { return refundStatus; }
    public void setRefundStatus(String refundStatus) { this.refundStatus = refundStatus; }
}