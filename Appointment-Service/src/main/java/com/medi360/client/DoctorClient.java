package com.medi360.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medi360.DTO.DoctorResponseDTO;

@FeignClient(name = "DOCTOR-SERVICE")
public interface DoctorClient {

    // ✅ Fixed: returns DoctorResponseDTO wrapper not DoctorDTO directly
    // Doctor Service getDoctorById now returns DoctorResponseDTO { doctor, statusCode, message }
    @GetMapping("/api/doctor/get/{id}")
    DoctorResponseDTO getDoctorById(@PathVariable("id") int id);
}