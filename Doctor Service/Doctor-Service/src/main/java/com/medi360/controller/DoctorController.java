package com.medi360.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medi360.DTO.DoctorDTO;
import com.medi360.DTO.DoctorResponseDTO;
import com.medi360.entities.Doctor;
import com.medi360.exception.DoctorNotFoundException;
import com.medi360.service.DoctorService;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @PostMapping("/add")
    public ResponseEntity<DoctorResponseDTO> addDoctor(@RequestBody DoctorDTO doctorDTO) {
        Doctor saved = doctorService.addDoctor(doctorDTO.getDoctor());
        return ResponseEntity.status(201).body(mapToDTO(saved));
    }

    @PutMapping("/update")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@RequestBody DoctorDTO doctorDTO) {
        Doctor doctor = doctorService.updateDoctor(doctorDTO.getDoctor());
        return ResponseEntity.ok(mapToDTO(doctor));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDoctor(
            @PathVariable("id") int id) throws DoctorNotFoundException {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(
            @PathVariable("id") int id) throws DoctorNotFoundException {
        return ResponseEntity.ok(mapToDTO(doctorService.getDoctorById(id)));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/getAllPaginated")
    public ResponseEntity<Page<Doctor>> getAllDoctorsWithPagination(
            @RequestParam("pgno") int pgno,
            @RequestParam("size") int size,
            @RequestParam("sorting") String sorting,
            @RequestParam("asc") boolean asc) {
        Sort sort = asc ? Sort.by(sorting).ascending() : Sort.by(sorting).descending();
        Pageable pageable = PageRequest.of(pgno, size, sort);
        return ResponseEntity.ok(doctorService.getAllDoctorsWithPagination(pageable));
    }
    @GetMapping("/getbyemail")
    public ResponseEntity<DoctorResponseDTO> getDoctorByEmail(
            @RequestParam("email") String email) throws DoctorNotFoundException {
        return ResponseEntity.ok(mapToDTO(doctorService.getDoctorByEmail(email)));
    }
    @GetMapping("/by-email")
    public ResponseEntity<DoctorResponseDTO> getDoctorByEmailAlias(
            @RequestParam("email") String email) throws DoctorNotFoundException {
        return ResponseEntity.ok(mapToDTO(doctorService.getDoctorByEmail(email)));
    }
    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchByName(
            @RequestParam("name") String name) {
        return ResponseEntity.ok(doctorService.getDoctorsByName(name));
    }
    private DoctorResponseDTO mapToDTO(Doctor doctor) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.setDoctor(doctor);
        return dto;
    }
}