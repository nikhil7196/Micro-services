
package com.medi360.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

import jakarta.persistence.ManyToOne;

@Entity
public class Bed {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer bedId;
	@ManyToOne
	@JoinColumn(name="ward_id")
	@JsonIgnoreProperties("beds")
	private Ward ward;
	
	@Column(nullable = false)
	private int patientId;
	private String bedStatus;
	public Integer getBedId() {
		return bedId;
	}
	public void setBedId(Integer bedId) {
		this.bedId = bedId;
	}
	public Ward getWard() {
		return ward;
	}
	public void setWard(Ward ward) {
		this.ward = ward;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public String getBedStatus() {
		return bedStatus;
	}
	public void setBedStatus(String bedStatus) {
		this.bedStatus = bedStatus;
	}
	public Bed(Ward ward, int patientId, String bedStatus) {
		super();
		this.ward = ward;
		this.patientId = patientId;
		this.bedStatus = bedStatus;
	}
	public Bed() {
		super();
	}
	
}
