package com.medi360.DTO;

import com.medi360.entities.Patient;

import jakarta.validation.Valid;

public class PatientDTO {
	
	@Valid
	private Patient patient;

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
}