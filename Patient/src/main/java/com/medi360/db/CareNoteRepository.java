package com.medi360.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medi360.entities.CareNote;

import java.util.List;

public interface CareNoteRepository extends JpaRepository<CareNote, Long> {
	List<CareNote> findByPatient_PatientIdOrderByCreatedAtDesc(int patientId);

	void deleteByPatientPatientId(int patientId);
}