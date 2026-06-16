package com.medi360.DTO;

public class InsuranceClaimWithPatientDTO {

    private int insuranceClaimId;
    private PatientDTO patient;  
    private String policyNumber;
    private double amount;
    private String status;

    public int getInsuranceClaimId() { return insuranceClaimId; }
    public void setInsuranceClaimId(int insuranceClaimId) { this.insuranceClaimId = insuranceClaimId; }

    public PatientDTO getPatient() { return patient; }
    public void setPatient(PatientDTO patient) { this.patient = patient; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}