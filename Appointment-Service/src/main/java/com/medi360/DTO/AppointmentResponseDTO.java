package com.medi360.DTO;

public class AppointmentResponseDTO {

    private Object appointment; 
    private int statusCode;
    private String message;
    private boolean wasRescheduled;

    public Object getAppointment() { return appointment; }
    public void setAppointment(Object appointment) { this.appointment = appointment; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isWasRescheduled() { return wasRescheduled; }
    public void setWasRescheduled(boolean wasRescheduled) { this.wasRescheduled = wasRescheduled; }
}