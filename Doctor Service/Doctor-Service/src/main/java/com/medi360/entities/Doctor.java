package com.medi360.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String department;

    @Column
    private String availabilitySchedule;

    @Column(unique = true)
    private String email;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getAvailabilitySchedule() { return availabilitySchedule; }
    public void setAvailabilitySchedule(String availabilitySchedule) {
        this.availabilitySchedule = availabilitySchedule;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Doctor(String name, String department, String availabilitySchedule) {
        super();
        this.name = name;
        this.department = department;
        this.availabilitySchedule = availabilitySchedule;
    }

    public Doctor() { super(); }
}