package com.medi360.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medi360.entities.Vitals;

import java.util.List;

public interface VitalsRepository extends JpaRepository<Vitals, Long> {
	List<Vitals> findByPatient_PatientIdOrderByRecordedAtDesc(int patientId);

	void deleteByPatientPatientId(int patientId);
}