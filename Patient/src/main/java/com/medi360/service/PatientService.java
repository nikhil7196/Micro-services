package com.medi360.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.medi360.db.CareNoteRepository;
import com.medi360.db.PatientRepository;
import com.medi360.db.VitalsRepository;
import com.medi360.entities.Patient;
import com.medi360.exception.PatientNotFoundException;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    CareNoteRepository careNoteRepository; // ✅ Added

    @Autowired
    VitalsRepository vitalsRepository;     // ✅ Added

    public Patient addPatient(Patient patient) {
        return this.patientRepository.save(patient);
    }

    public Patient getPatientById(int id) throws PatientNotFoundException {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id " + id));
    }

    public List<Patient> getPatientByName(String name) {
        // ✅ Fixed: returns empty list instead of throwing exception
        return patientRepository.findByPatientNameContainingIgnoreCase(name);
    }

    public Patient updatePatient(Patient patient) throws PatientNotFoundException {
        if (!patientRepository.existsById(patient.getPatientId())) {
            throw new PatientNotFoundException("Patient not found with id " + patient.getPatientId());
        }
        return this.patientRepository.save(patient);
    }

    @Transactional // ✅ All deletes happen in one transaction
    public String deletePatient(int id) throws PatientNotFoundException {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with id " + id);
        }

        // ✅ Delete related records first to avoid foreign key constraint violation
        careNoteRepository.deleteByPatientPatientId(id);
        vitalsRepository.deleteByPatientPatientId(id);

        this.patientRepository.deleteById(id);
        return "Successfully deleted";
    }

    public List<Patient> getAllPatients() {
        return this.patientRepository.findAll();
    }

    public Page<Patient> getAllPatientsWithPagination(Pageable pageable) {
        return this.patientRepository.findAll(pageable);
    }

    public List<Patient> searchByName(String name) {
        return patientRepository.findByPatientNameContainingIgnoreCase(name);
    }
}