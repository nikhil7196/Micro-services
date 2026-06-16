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
    public InsuranceClaim getInsuranceClaimById(int id)
            throws InsuranceClaimNotFoundException {
        return insuranceClaimRepository.findById(id)
                .orElseThrow(() -> new InsuranceClaimNotFoundException(
                        "InsuranceClaim not found with id " + id));
    }
    public InsuranceClaimWithPatientDTO getInsuranceClaimByIdWithPatient(int id)
            throws InsuranceClaimNotFoundException {

        InsuranceClaim claim = insuranceClaimRepository.findById(id)
                .orElseThrow(() -> new InsuranceClaimNotFoundException(
                        "InsuranceClaim not found with id " + id));

        return buildDTO(claim);
    }
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
    public InsuranceClaim updateClaimStatus(int claimId, String status)
            throws InsuranceClaimNotFoundException {

        InsuranceClaim claim = insuranceClaimRepository.findById(claimId)
                .orElseThrow(() -> new InsuranceClaimNotFoundException(
                        "Insurance claim not found with id " + claimId));

        claim.setStatus(status);
        return insuranceClaimRepository.save(claim);
    }
    public String deleteInsuranceClaim(int id)
            throws InsuranceClaimNotFoundException {

        if (!insuranceClaimRepository.existsById(id)) {
            throw new InsuranceClaimNotFoundException(
                    "Insurance claim not found with id " + id);
        }

        insuranceClaimRepository.deleteById(id);
        return "Successfully deleted insurance claim";
    }
    public List<InsuranceClaimWithPatientDTO> getAllInsuranceClaimsWithPatient() {
        return insuranceClaimRepository.findAll()
                .stream()
                .map(this::buildDTO)
                .collect(Collectors.toList());
    }
    public Page<InsuranceClaimWithPatientDTO> getAllInsuranceClaimsWithPatientPaginated(Pageable pageable) {
        return insuranceClaimRepository.findAll(pageable)
                .map(this::buildDTO);
    }
    public List<InsuranceClaim> getClaimsByStatus(String status) {
        return insuranceClaimRepository.findByStatus(status);
    }
    public List<InsuranceClaim> getClaimsByPatient(int patientId) {
        return insuranceClaimRepository.findByPatient_PatientId(patientId);
    }
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
    private PatientDTO fallbackPatient(int patientId) {
        PatientDTO fallback = new PatientDTO();
        fallback.setPatientId(patientId);
        fallback.setPatientName("Unknown");
        fallback.setPatientStatus("Unknown");
        return fallback;
    }
}