package com.medi360.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medi360.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>{
	List<Patient> findByPatientNameContainingIgnoreCase(String patientName);
	long count();
	long countByPatientStatus(String status);
	long countByPatientGender(String gender);
}
