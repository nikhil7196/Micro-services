package com.medi360.DTO;

public class PatientDTO {

    private int patientId;
    private String patientName;
    private String patientStatus;
    public PatientDTO() {}

    public PatientDTO(int patientId, String patientName, String patientStatus) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientStatus = patientStatus;
    }
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(String patientStatus) {
        this.patientStatus = patientStatus;
    }
}