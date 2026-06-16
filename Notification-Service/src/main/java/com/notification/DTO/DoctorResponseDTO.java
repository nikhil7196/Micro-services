package com.notification.DTO;
public class DoctorResponseDTO {

    private DoctorData doctor;
    private int statusCode;
    private String message;

    public DoctorData getDoctor() { return doctor; }
    public void setDoctor(DoctorData doctor) { this.doctor = doctor; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public int getDoctorId() {
        return doctor != null ? doctor.getId() : 0;
    }

    public static class DoctorData {
        private int id;
        private String name;
        private String department;
        private String availabilitySchedule;
        private String email;

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }

        public String getAvailabilitySchedule() { return availabilitySchedule; }
        public void setAvailabilitySchedule(String s) { this.availabilitySchedule = s; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}