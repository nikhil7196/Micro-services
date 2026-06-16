package com.notification.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.notification.DTO.DoctorResponseDTO;

@FeignClient(name = "DOCTOR-SERVICE")
public interface DoctorFeign {
    @GetMapping("/api/doctor/get/{id}")
    ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable("id") int id);

    @GetMapping("/api/doctor/getbyemail")
    ResponseEntity<DoctorResponseDTO> getDoctorByEmail(@RequestParam("email") String email);
}