package com.medi360.DTO;

public class PatientDTO {

    private int patientId;
    private String patientName;
    private String patientGender;       // ✅ needed
    private String patientDOB;          // ✅ needed
    private String patientPhoneNumber;  // ✅ needed
    private String patientMedicalHistory; // ✅ needed
    private String patientStatus;

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getPatientGender() { return patientGender; }
    public void setPatientGender(String patientGender) { this.patientGender = patientGender; }

    public String getPatientDOB() { return patientDOB; }
    public void setPatientDOB(String patientDOB) { this.patientDOB = patientDOB; }

    public String getPatientPhoneNumber() { return patientPhoneNumber; }
    public void setPatientPhoneNumber(String patientPhoneNumber) { this.patientPhoneNumber = patientPhoneNumber; }

    public String getPatientMedicalHistory() { return patientMedicalHistory; }
    public void setPatientMedicalHistory(String patientMedicalHistory) { this.patientMedicalHistory = patientMedicalHistory; }

    public String getPatientStatus() { return patientStatus; }
    public void setPatientStatus(String patientStatus) { this.patientStatus = patientStatus; }
}