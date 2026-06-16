package com.medi360.DTO;

public class DoctorResponseDTO {

    // ✅ 'doctor' field name must match Doctor Service response
    private DoctorDTO doctor;
    private int statusCode;
    private String message;

    public DoctorDTO getDoctor() { return doctor; }
    public void setDoctor(DoctorDTO doctor) { this.doctor = doctor; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}