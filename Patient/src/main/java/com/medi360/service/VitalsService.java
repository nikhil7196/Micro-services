
package com.medi360.service;

import com.medi360.entities.Patient;
import com.medi360.entities.Vitals;
import com.medi360.DTO.VitalsRequestDTO;
import com.medi360.DTO.VitalsResponseDTO;
import com.medi360.db.PatientRepository;
import com.medi360.db.VitalsRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VitalsService {

    private final VitalsRepository vitalsRepository;
    private final PatientRepository patientRepository;

    public VitalsService(VitalsRepository vitalsRepository, PatientRepository patientRepository) {
        this.vitalsRepository = vitalsRepository;
        this.patientRepository = patientRepository;
    }
 // VitalsService.java — updated methods
    public VitalsResponseDTO addVitals(VitalsRequestDTO request) {
        Patient patient = patientRepository.findById(request.getPatientId())
            .orElseThrow(() -> new RuntimeException("Patient not found: " + request.getPatientId()));

        Vitals vitals = new Vitals();
        vitals.setPatient(patient);
        vitals.setNurseId(request.getNurseId());
        vitals.setBloodPressure(request.getBloodPressure());
        vitals.setTemperature(request.getTemperature());
        vitals.setPulseRate(request.getPulseRate());
        vitals.setSpo2(request.getSpo2());

        Vitals saved = vitalsRepository.save(vitals);
        return toResponseDTO(saved);
    }

    public List<VitalsResponseDTO> getVitalsByPatient(int patientId) {
        return vitalsRepository
            .findByPatient_PatientIdOrderByRecordedAtDesc(patientId)
            .stream()
            .map(this::toResponseDTO)
            .toList();
    }

    public VitalsResponseDTO getVitalsById(Long vitalId) {
        Vitals vitals = vitalsRepository.findById(vitalId)
            .orElseThrow(() -> new RuntimeException("Vitals record not found: " + vitalId));
        return toResponseDTO(vitals);
    }

    private VitalsResponseDTO toResponseDTO(Vitals vitals) {
        VitalsResponseDTO dto = new VitalsResponseDTO();
        dto.setVitalId(vitals.getVitalId());
        dto.setPatientId(vitals.getPatient().getPatientId());       
        dto.setPatientName(vitals.getPatient().getPatientName());  
        dto.setNurseId(vitals.getNurseId());
        dto.setBloodPressure(vitals.getBloodPressure());
        dto.setTemperature(vitals.getTemperature());
        dto.setPulseRate(vitals.getPulseRate());
        dto.setSpo2(vitals.getSpo2());
        dto.setRecordedAt(vitals.getRecordedAt());
        return dto;
    }
}