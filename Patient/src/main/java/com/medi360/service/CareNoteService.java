package com.medi360.service;
import org.springframework.stereotype.Service;

import com.medi360.DTO.CareNoteDTO;
import com.medi360.DTO.CareNoteResponseDTO;
import com.medi360.db.CareNoteRepository;
import com.medi360.db.PatientRepository;
import com.medi360.entities.CareNote;
import com.medi360.entities.Patient;

import java.util.List;

@Service
public class CareNoteService {

 private final CareNoteRepository careNoteRepository;
 private final PatientRepository patientRepository;

 public CareNoteService(CareNoteRepository careNoteRepository, PatientRepository patientRepository) {
     this.careNoteRepository = careNoteRepository;
     this.patientRepository = patientRepository;
 }
 
 public CareNoteResponseDTO addCareNote(CareNoteDTO request) {
	    Patient patient = patientRepository.findById(request.getPatientId())
	        .orElseThrow(() -> new RuntimeException("Patient not found: " + request.getPatientId()));

	    CareNote note = new CareNote();
	    note.setPatient(patient);
	    note.setNurseId(request.getNurseId());
	    note.setNote(request.getNote());

	    CareNote saved = careNoteRepository.save(note);
	    return toResponseDTO(saved);
	}

	public List<CareNoteResponseDTO> getCareNotesByPatient(int patientId) {
	    return careNoteRepository
	        .findByPatient_PatientIdOrderByCreatedAtDesc(patientId)
	        .stream()
	        .map(this::toResponseDTO)
	        .toList();
	}

	public CareNoteResponseDTO getCareNoteById(Long noteId) {
	    CareNote note = careNoteRepository.findById(noteId)
	        .orElseThrow(() -> new RuntimeException("Care note not found: " + noteId));
	    return toResponseDTO(note);
	}

	private CareNoteResponseDTO toResponseDTO(CareNote note) {
	    CareNoteResponseDTO dto = new CareNoteResponseDTO();
	    dto.setNoteId(note.getNoteId());
	    dto.setPatientId(note.getPatient().getPatientId());
	    dto.setPatientName(note.getPatient().getPatientName());  // adjust field name to match your Patient entity
	    dto.setNurseId(note.getNurseId());
	    dto.setNote(note.getNote());
	    dto.setCreatedAt(note.getCreatedAt());
	    return dto;
	}
}