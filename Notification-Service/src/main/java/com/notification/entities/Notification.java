package com.notification.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notification {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int notificationId;
	
	private String message;
	private String category;
	private String status;
	private LocalDateTime createdDate;
	
//	@ManyToOne
//	@JoinColumn(name="doctor_id")
	private int doctorId;
	
//	@ManyToOne
//	@JoinColumn(name="patient_id")
	private int patientId;

	public Notification(String message, String category, String status, LocalDateTime createdDate,int patientId,int doctorId) {
		super();
		this.message = message;
		this.category = category;
		this.status = status;
		this.createdDate = createdDate;
		this.doctorId = doctorId;
		this.patientId=patientId;
	}

	public Notification() {
		super();
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	
	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patient) {
		this.patientId = patient;
	}
	
	
}
