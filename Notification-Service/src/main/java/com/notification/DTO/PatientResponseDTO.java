package com.notification.DTO;
public class PatientResponseDTO {

    private PatientData patient;
    private int statusCode;
    private String message;

    public PatientData getPatient() { return patient; }
    public void setPatient(PatientData patient) { this.patient = patient; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public int getPatientId() {
        return patient != null ? patient.getPatientId() : 0;
    }

    public static class PatientData {
        private int patientId;
        private String patientName;

        public int getPatientId() { return patientId; }
        public void setPatientId(int patientId) { this.patientId = patientId; }

        public String getPatientName() { return patientName; }
        public void setPatientName(String patientName) { this.patientName = patientName; }
    }
}