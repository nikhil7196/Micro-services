package com.medi360.DTO;

import java.util.List;
import com.medi360.entities.Patient;

public class PatientListResponseDTO {

    private List<Patient> patients;
    private int statusCode;
    private String message;

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}