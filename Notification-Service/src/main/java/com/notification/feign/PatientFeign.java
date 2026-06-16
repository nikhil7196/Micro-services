package com.notification.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.notification.DTO.PatientResponseDTO;

@FeignClient(name = "PATIENT-SERVICE")
public interface PatientFeign {
    @GetMapping("/api/patient/getPatientById/{id}")
    ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable("id") int id);
}