package com.medi360.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.medi360.DTO.InsuranceClaimWithPatientDTO;
import com.medi360.DTO.PatientDTO;
import com.medi360.DTO.PatientResponseDTO;
import com.medi360.client.PatientClient;
import com.medi360.db.InsuranceClaimRepository;
import com.medi360.entities.InsuranceClaim;
import com.medi360.exception.InsuranceClaimNotFoundException;
import com.medi360.exception.PatientNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class InsuranceClaimService {

    @Autowired
    private InsuranceClaimRepository insuranceClaimRepository;

    @Autowired
    private PatientClient patientClient;

    // ✅ ADD
    public InsuranceClaim addInsuranceClaim(InsuranceClaim claim)
            throws PatientNotFoundException {

        int patientId = claim.getPatient().getPatientId();

        try {
            PatientResponseDTO response = patientClient.getPatientById(patientId);
            if (response == null || response.getPatient() == null) {
                throw new PatientNotFoundException("Patient not found with id " + patientId);
            }
        } catch (PatientNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new PatientNotFoundException("Patient not found with id " + patientId);
        }

        return insuranceClaimRepository.save(claim);
    }

    // ✅ GET BY ID — raw
    public InsuranceClaim getInsuranceClaimById(int id)
            throws InsuranceClaimNotFoundException {
        return insuranceClaimRepository.findById(id)
                .orElseThrow(() -> new InsuranceClaimNotFoundException(
                        "InsuranceClaim not found with id " + id));
    }

    // ✅ GET BY ID WITH PATIENT NAME
    public InsuranceClaimWithPatientDTO getInsuranceClaimByIdWithPatient(int id)
            throws InsuranceClaimNotFoundException {

        InsuranceClaim claim = insuranceClaimRepository.findById(id)
                .orElseThrow(() -> new InsuranceClaimNotFoundException(
                        "InsuranceClaim not found with id " + id));

        return buildDTO(claim);
    }

    // ✅ UPDATE
    @Transactional
    public InsuranceClaim updateInsuranceClaim(InsuranceClaim incoming)
            throws InsuranceClaimNotFoundException, PatientNotFoundException {

        InsuranceClaim existing = insuranceClaimRepository
                .findById(incoming.getInsuranceClaimId())
                .orElseThrow(() -> new InsuranceClaimNotFoundException(
                        "Insurance claim not found with id " + incoming.getInsuranceClaimId()));

        int patientId = incoming.getPatient().getPatientId();

        try {
            PatientResponseDTO response = patientClient.getPatientById(patientId);
            if (response == null || response.getPatient() == null) {
                throw new PatientNotFoundException("Patient not found with id " + patientId);
            }
        } catch (PatientNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new PatientNotFoundException("Patient not found with id " + patientId);
        }

        existing.setPatient(incoming.getPatient());
        existing.setPolicyNumber(incoming.getPolicyNumber());
        existing.setAmount(incoming.getAmount());
        existing.setStatus(incoming.getStatus());

        return insuranceClaimRepository.save(existing);
    }

    // ✅ UPDATE STATUS
    public InsuranceClaim updateClaimStatus(int claimId, String status)
            throws InsuranceClaimNotFoundException {

        InsuranceClaim claim = insuranceClaimRepository.findById(claimId)
                .orElseThrow(() -> new InsuranceClaimNotFoundException(
                        "Insurance claim not found with id " + claimId));

        claim.setStatus(status);
        return insuranceClaimRepository.save(claim);
    }

    // ✅ DELETE
    public String deleteInsuranceClaim(int id)
            throws InsuranceClaimNotFoundException {

        if (!insuranceClaimRepository.existsById(id)) {
            throw new InsuranceClaimNotFoundException(
                    "Insurance claim not found with id " + id);
        }

        insuranceClaimRepository.deleteById(id);
        return "Successfully deleted insurance claim";
    }

    // ✅ GET ALL WITH PATIENT
    public List<InsuranceClaimWithPatientDTO> getAllInsuranceClaimsWithPatient() {
        return insuranceClaimRepository.findAll()
                .stream()
                .map(this::buildDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET ALL PAGINATED WITH PATIENT
    public Page<InsuranceClaimWithPatientDTO> getAllInsuranceClaimsWithPatientPaginated(Pageable pageable) {
        return insuranceClaimRepository.findAll(pageable)
                .map(this::buildDTO);
    }

    // ✅ GET BY STATUS — returns empty list instead of exception
    public List<InsuranceClaim> getClaimsByStatus(String status) {
        return insuranceClaimRepository.findByStatus(status);
    }

    // ✅ GET BY PATIENT
    public List<InsuranceClaim> getClaimsByPatient(int patientId) {
        return insuranceClaimRepository.findByPatient_PatientId(patientId);
    }

    // ✅ Helper — build enriched DTO with patient name
    private InsuranceClaimWithPatientDTO buildDTO(InsuranceClaim claim) {
        InsuranceClaimWithPatientDTO dto = new InsuranceClaimWithPatientDTO();
        dto.setInsuranceClaimId(claim.getInsuranceClaimId());
        dto.setPolicyNumber(claim.getPolicyNumber());
        dto.setAmount(claim.getAmount());
        dto.setStatus(claim.getStatus());

        int patientId = claim.getPatient().getPatientId();
        try {
            PatientResponseDTO response = patientClient.getPatientById(patientId);
            if (response != null && response.getPatient() != null) {
                dto.setPatient(response.getPatient());
            } else {
                dto.setPatient(fallbackPatient(patientId));
            }
        } catch (Exception e) {
            dto.setPatient(fallbackPatient(patientId));
        }

        return dto;
    }

    // ✅ Helper — fallback when Patient Service is unreachable
    private PatientDTO fallbackPatient(int patientId) {
        PatientDTO fallback = new PatientDTO();
        fallback.setPatientId(patientId);
        fallback.setPatientName("Unknown");
        fallback.setPatientStatus("Unknown");
        return fallback;
    }
}