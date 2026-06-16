package com.medi360.DTO;

public class CareNoteDTO {

	    private int patientId;
	    private Long nurseId;
	    private String note;

	    public int getPatientId() { return patientId; }
	    public void setPatientId(int patientId) { this.patientId = patientId; }

	    public Long getNurseId() { return nurseId; }
	    public void setNurseId(Long nurseId) { this.nurseId = nurseId; }

	    public String getNote() { return note; }
	    public void setNote(String note) { this.note = note; }
	}


