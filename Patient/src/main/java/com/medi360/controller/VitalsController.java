package com.medi360.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.medi360.DTO.VitalsRequestDTO;
import com.medi360.DTO.VitalsResponseDTO;
import com.medi360.service.VitalsService;
import java.util.List;

@RestController
@RequestMapping("/api/vitals")


public class VitalsController {

    private final VitalsService vitalsService;

    public VitalsController(VitalsService vitalsService) {
        this.vitalsService = vitalsService;
    }

    @PostMapping("/add")
    public ResponseEntity<VitalsResponseDTO> addVitals(
            @RequestBody VitalsRequestDTO request) {
        return ResponseEntity.ok(vitalsService.addVitals(request));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<VitalsResponseDTO>> getByPatient(
            @PathVariable("patientId") int patientId) { // ✅ explicit name
        return ResponseEntity.ok(vitalsService.getVitalsByPatient(patientId));
    }

    @GetMapping("/{vitalId}")
    public ResponseEntity<VitalsResponseDTO> getById(
            @PathVariable("vitalId") Long vitalId) { // ✅ explicit name
        return ResponseEntity.ok(vitalsService.getVitalsById(vitalId));
    }
}