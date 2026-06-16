package com.medi360.DTO;

public class DoctorDTO {

    private int id;                      // ✅ matches Doctor entity id
    private String name;
    private String department;
    private String availabilitySchedule; // ✅ matches Doctor entity field
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