package com.medi360.DTO;
import java.time.LocalDateTime;

public class CareNoteResponseDTO {
	    private Long noteId;
	    private int patientId;
	    private String patientName;     
	    private Long nurseId;
	    private String note;
	    private LocalDateTime createdAt;
	    
	    public Long getNoteId() { return noteId; }
	    public void setNoteId(Long noteId) { this.noteId = noteId; }

	    public int getPatientId() { return patientId; }
	    public void setPatientId(int patientId) { this.patientId = patientId; }

	    public String getPatientName() { return patientName; }
	    public void setPatientName(String patientName) { this.patientName = patientName; }

	    public Long getNurseId() { return nurseId; }
	    public void setNurseId(Long nurseId) { this.nurseId = nurseId; }

	    public String getNote() { return note; }
	    public void setNote(String note) { this.note = note; }

	    public LocalDateTime getCreatedAt() { return createdAt; }
	    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	}
