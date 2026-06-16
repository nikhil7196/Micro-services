package com.medi360.service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medi360.DTO.PatientResponseDTO;
import com.medi360.client.PatientClient;
import com.medi360.db.BedRepository;
import com.medi360.db.WardRepository;
import com.medi360.entities.Bed;
import com.medi360.entities.Ward;
import com.medi360.exception.BedNotFoundException;

@Service
public class BedService {

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private PatientClient patientClient;
    public Bed createBed(Bed bed) {

        if (bed.getWard() == null || bed.getWard().getWardId() == 0) {
            throw new IllegalArgumentException("Ward ID is required");
        }

        int wardId = bed.getWard().getWardId();

        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Ward not found with id " + wardId));

        List<Bed> existingBeds = bedRepository.findByWard_WardId(wardId);

        if (existingBeds.size() >= ward.getWardcapacity()) {
            throw new IllegalStateException("Ward is full");
        }

        bed.setWard(ward);
        return bedRepository.save(bed);
    }
    public List<Bed> getAllBeds() {
        return bedRepository.findAll();
    }
    public Bed getBedById(int bedId) {
        return bedRepository.findById(bedId).orElse(null);
    }
    public List<Bed> getBedByWard(int wardId) {
        return bedRepository.findByWard_WardId(wardId);
    }
    public Bed updateBed(Bed bed) throws BedNotFoundException {

        Bed existingBed = bedRepository.findById(bed.getBedId())
                .orElseThrow(() -> new BedNotFoundException("Bed not found"));

        if (bed.getBedStatus() != null) {
            existingBed.setBedStatus(bed.getBedStatus());
        }

        if (bed.getWard() != null && bed.getWard().getWardId() != 0) {
            Ward ward = wardRepository.findById(bed.getWard().getWardId())
                    .orElseThrow(() -> new IllegalArgumentException("Ward not found"));
            existingBed.setWard(ward);
        }

        return bedRepository.save(existingBed);
    }
    public void delete(int bedId) throws BedNotFoundException {

        if (!bedRepository.existsById(bedId)) {
            throw new BedNotFoundException("Bed not found with id " + bedId);
        }

        bedRepository.deleteById(bedId);
    }
    public Page<Bed> getAllBedsWithPaginated(Pageable pageable) {
        return bedRepository.findAll(pageable);
    }
    public Bed assignPatientToBed(int bedId, int patientId)
            throws BedNotFoundException {

        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new BedNotFoundException("Bed not found"));

        if ("OCCUPIED".equalsIgnoreCase(bed.getBedStatus())) {
            throw new IllegalStateException("Bed already occupied");
        }
        try {
            PatientResponseDTO response = patientClient.getPatientById(patientId);
            if (response == null || response.getPatient() == null) {
                throw new RuntimeException("Invalid patient ID: " + patientId);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Invalid patient ID: " + patientId);
        }

        bed.setPatientId(patientId);
        bed.setBedStatus("OCCUPIED");

        return bedRepository.save(bed);
    }
    public Bed dischargePatient(int bedId) throws BedNotFoundException {

        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new BedNotFoundException("Bed not found"));

        bed.setPatientId(0);
        bed.setBedStatus("AVAILABLE");

        return bedRepository.save(bed);
    }
}