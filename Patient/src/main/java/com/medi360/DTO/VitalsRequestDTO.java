package com.medi360.DTO;

public class VitalsRequestDTO {

	    private int patientId;
	    private Long nurseId;
	    private String bloodPressure;  
	    private Double temperature;    
	    private Integer pulseRate;     
	    private Integer spo2;          
	    public int getPatientId() { return patientId; }
	    public void setPatientId(int patientId) { this.patientId = patientId; }

	    public Long getNurseId() { return nurseId; }
	    public void setNurseId(Long nurseId) { this.nurseId = nurseId; }

	    public String getBloodPressure() { return bloodPressure; }
	    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }

	    public Double getTemperature() { return temperature; }
	    public void setTemperature(Double temperature) { this.temperature = temperature; }

	    public Integer getPulseRate() { return pulseRate; }
	    public void setPulseRate(Integer pulseRate) { this.pulseRate = pulseRate; }

	    public Integer getSpo2() { return spo2; }
	    public void setSpo2(Integer spo2) { this.spo2 = spo2; }
	}
