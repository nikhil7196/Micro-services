package com.medi360.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medi360.DTO.PatientResponseDTO;

@FeignClient(name = "PATIENT-SERVICE")
public interface PatientClient {
    @GetMapping("/api/patient/getPatientById/{id}")
    PatientResponseDTO getPatientById(@PathVariable("id") int id);
}