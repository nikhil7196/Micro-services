package com.medi360.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medi360.DTO.InsuranceClaimDTO;
import com.medi360.DTO.InsuranceClaimResponse;
import com.medi360.DTO.InsuranceClaimWithPatientDTO;
import com.medi360.entities.InsuranceClaim;
import com.medi360.exception.InsuranceClaimNotFoundException;
import com.medi360.exception.PatientNotFoundException;
import com.medi360.service.InsuranceClaimService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceClaimController {

    @Autowired
    InsuranceClaimService insuranceClaimService;

    // ✅ ADD
    @PostMapping("/addInsuranceClaim")
    public ResponseEntity<InsuranceClaimResponse> addInsuranceClaim(
            @Valid @RequestBody InsuranceClaimDTO insuranceClaimDTO)
            throws PatientNotFoundException {

        InsuranceClaim i = this.insuranceClaimService.addInsuranceClaim(
                insuranceClaimDTO.getInsuranceClaim());

        InsuranceClaimResponse dto = new InsuranceClaimResponse();
        dto.setInsuranceClaim(i);
        dto.setStatusCode(201);
        dto.setMessage("InsuranceClaim created successfully");

        return ResponseEntity.status(201).body(dto);
    }

    // ✅ GET BY ID — wrapped in response with patient name
    @GetMapping("/getInsuranceClaimById/{id}")
    public ResponseEntity<InsuranceClaimResponse> getInsuranceClaimById(
            @PathVariable("id") int id) throws InsuranceClaimNotFoundException {

        InsuranceClaimWithPatientDTO claim =
                insuranceClaimService.getInsuranceClaimByIdWithPatient(id);

        InsuranceClaimResponse dto = new InsuranceClaimResponse();
        dto.setInsuranceClaim(claim); // ✅ wrapped — frontend reads res.data.insuranceClaim
        dto.setStatusCode(200);
        dto.setMessage("InsuranceClaim record retrieved successfully");

        return ResponseEntity.ok(dto);
    }

    // ✅ UPDATE
    @PutMapping("/updateInsuranceClaim")
    public ResponseEntity<InsuranceClaimResponse> updateInsuranceClaim(
            @Valid @RequestBody InsuranceClaimDTO insuranceClaimDTO)
            throws InsuranceClaimNotFoundException, PatientNotFoundException {

        InsuranceClaim i = this.insuranceClaimService.updateInsuranceClaim(
                insuranceClaimDTO.getInsuranceClaim());

        InsuranceClaimResponse dto = new InsuranceClaimResponse();
        dto.setInsuranceClaim(i);
        dto.setStatusCode(200);
        dto.setMessage("InsuranceClaim updated successfully");

        return ResponseEntity.status(200).body(dto);
    }

    // ✅ UPDATE STATUS
    @PutMapping("/updateInsuranceClaimStatus/{claimId}")
    public ResponseEntity<InsuranceClaimResponse> updateClaimStatus(
            @PathVariable("claimId") int claimId,
            @RequestParam("status") String status) throws InsuranceClaimNotFoundException {

        InsuranceClaim claim = insuranceClaimService.updateClaimStatus(claimId, status);

        InsuranceClaimResponse dto = new InsuranceClaimResponse();
        dto.setInsuranceClaim(claim);
        dto.setStatusCode(200);
        dto.setMessage("Insurance claim status updated successfully");

        return ResponseEntity.ok(dto);
    }

    // ✅ DELETE
    @DeleteMapping("/deleteInsuranceClaim/{id}")
    public ResponseEntity<String> deleteInsuranceClaim(
            @PathVariable("id") int id) throws InsuranceClaimNotFoundException {
        return ResponseEntity.ok(this.insuranceClaimService.deleteInsuranceClaim(id));
    }

    // ✅ GET ALL WITH PATIENT
    @GetMapping("/fetchAllInsuranceClaims")
    public ResponseEntity<List<InsuranceClaimWithPatientDTO>> getAllInsuranceClaims() {
        return ResponseEntity.ok(insuranceClaimService.getAllInsuranceClaimsWithPatient());
    }

    // ✅ GET BY STATUS
    @GetMapping("/getInsuranceClaimsByStatus/{status}")
    public ResponseEntity<List<InsuranceClaim>> getClaimsByStatus(
            @PathVariable("status") String status) throws InsuranceClaimNotFoundException {
        return ResponseEntity.ok(insuranceClaimService.getClaimsByStatus(status));
    }

    // ✅ PAGINATED WITH PATIENT
    @GetMapping("/fetchAllInsuranceClaimsPaginated")
    public ResponseEntity<Page<InsuranceClaimWithPatientDTO>> getAllInsuranceClaimsPaginated(
            @RequestParam("pgno") int pgno,
            @RequestParam("size") int size,
            @RequestParam("sorting") String sorting,
            @RequestParam("asc") boolean asc) {

        Sort sort = asc ? Sort.by(sorting).ascending() : Sort.by(sorting).descending();
        Pageable pageable = PageRequest.of(pgno, size, sort);

        return ResponseEntity.ok(
                insuranceClaimService.getAllInsuranceClaimsWithPatientPaginated(pageable));
    }
}