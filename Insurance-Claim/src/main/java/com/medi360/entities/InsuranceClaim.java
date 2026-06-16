package com.medi360.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "InsuranceClaim")
public class InsuranceClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int insuranceClaimId;
    @Embedded
    private PatientRef patient;

    @Column
    @NotBlank
    @Size(max = 50)
    private String policyNumber;

    @Column
    @Positive
    private double amount;

    @Column
    @NotBlank
    private String status;

    public int getInsuranceClaimId() { return insuranceClaimId; }
    public void setInsuranceClaimId(int insuranceClaimId) { this.insuranceClaimId = insuranceClaimId; }

    public PatientRef getPatient() { return patient; }
    public void setPatient(PatientRef patient) { this.patient = patient; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public InsuranceClaim() {}
}