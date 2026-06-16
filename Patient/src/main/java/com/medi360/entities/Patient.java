
package com.medi360.entities;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientId;

    @Column
    private String patientName;

    @Column
    private LocalDate patientDOB;
    
    @Column
    private String patientGender;

    @Column
    private String patientPhoneNumber;

    @Column(length = 500)
    private String patientMedicalHistory;

    @Column
    private String patientStatus;

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

	public LocalDate getPatientDOB() {
		return patientDOB;
	}

	public void setPatientDOB(LocalDate patientDOB) {
		this.patientDOB = patientDOB;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

	public String getPatientPhoneNumber() {
		return patientPhoneNumber;
	}

	public void setPatientPhoneNumber(String patientPhoneNumber) {
		this.patientPhoneNumber = patientPhoneNumber;
	}

	public String getPatientMedicalHistory() {
		return patientMedicalHistory;
	}

	public Patient(String patientName, LocalDate patientDOB, String patientGender, String patientPhoneNumber,
			String patientMedicalHistory, String patientStatus) {
		super();
		this.patientName = patientName;
		this.patientDOB = patientDOB;
		this.patientGender = patientGender;
		this.patientPhoneNumber = patientPhoneNumber;
		this.patientMedicalHistory = patientMedicalHistory;
		this.patientStatus = patientStatus;
	}

	public void setPatientMedicalHistory(String patientMedicalHistory) {
		this.patientMedicalHistory = patientMedicalHistory;
	}

	public String getPatientStatus() {
		return patientStatus;
	}

	public void setPatientStatus(String patientStatus) {
		this.patientStatus = patientStatus;
	}
	
	public Patient() {
	   
	}
	

   
}
