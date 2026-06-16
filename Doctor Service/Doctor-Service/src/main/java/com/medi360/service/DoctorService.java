package com.medi360.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.medi360.db.DoctorRepository;
import com.medi360.entities.Doctor;
import com.medi360.exception.DoctorNotFoundException;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(int doctorId) throws DoctorNotFoundException {
        if (!doctorRepository.existsById(doctorId)) {
            throw new DoctorNotFoundException("Doctor not found with id " + doctorId);
        }
        doctorRepository.deleteById(doctorId);
    }

    public Doctor getDoctorById(int id) throws DoctorNotFoundException {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id " + id));
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Page<Doctor> getAllDoctorsWithPagination(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }

    public Doctor getDoctorByEmail(String email) throws DoctorNotFoundException {
        return doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with email: " + email));
    }

    public List<Doctor> getDoctorsByName(String name) {
        return doctorRepository.findByNameContainingIgnoreCase(name);
    }
}