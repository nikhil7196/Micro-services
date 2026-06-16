package com.medi360.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentWithDetailsDTO {

    private int id;
    private LocalDate date;
    private LocalTime time;
    private int durationMinutes;
    private String status;
    private String reason;
    private PatientDTO patient; 
    private DoctorDTO doctor;   

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public PatientDTO getPatient() { return patient; }
    public void setPatient(PatientDTO patient) { this.patient = patient; }

    public DoctorDTO getDoctor() { return doctor; }
    public void setDoctor(DoctorDTO doctor) { this.doctor = doctor; }
}