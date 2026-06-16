package com.notification.DTO;

public class NotificationDTO {

    private int userID;   
    private int patientId;
    private int doctorId;
    private String message;
    private String category;
    private String status;

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}