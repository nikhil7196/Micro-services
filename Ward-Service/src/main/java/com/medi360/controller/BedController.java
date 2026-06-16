package com.medi360.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medi360.DTO.BedDTO;
import com.medi360.DTO.BedResponseDTO;
import com.medi360.entities.Bed;
import com.medi360.exception.BedNotFoundException;
import com.medi360.service.BedService;

@RestController
@RequestMapping("/api/beds")
public class BedController {

    @Autowired
    private BedService bedService;

    @PostMapping("/create")
    public ResponseEntity<BedResponseDTO> createBed(@RequestBody BedDTO bedDTO) {
        // ✅ Removed System.out.println("Okkk")
        Bed bed = bedService.createBed(bedDTO.getBed());
        BedResponseDTO response = new BedResponseDTO();
        response.setBed(bed);
        response.setStatusCode(201);
        response.setMessage("Bed created successfully");
        return ResponseEntity.status(201).body(response);
    }

    // ✅ Fixed: was returning 201 for a GET — changed to 200
    @GetMapping("/getAllBeds")
    public ResponseEntity<List<Bed>> getAllBeds() {
        return ResponseEntity.ok(bedService.getAllBeds());
    }

    @GetMapping("/getBed/{bedId}")
    public ResponseEntity<BedResponseDTO> getBedById(
            @PathVariable("bedId") int bedId) { // ✅ explicit name
        Bed bed = bedService.getBedById(bedId);
        BedResponseDTO dto = new BedResponseDTO();
        dto.setBed(bed);
        dto.setMessage("Found bed with ID: " + bedId);
        dto.setStatusCode(200);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/updateBed")
    public ResponseEntity<BedResponseDTO> updateBed(
            @RequestBody BedDTO bedDTO) throws BedNotFoundException {
        Bed updatedBed = bedService.updateBed(bedDTO.getBed());
        BedResponseDTO dto = new BedResponseDTO();
        dto.setBed(updatedBed);
        dto.setStatusCode(200);
        dto.setMessage("Bed updated successfully");
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{bedId}")
    public ResponseEntity<String> deleteBed(
            @PathVariable("bedId") int bedId) throws BedNotFoundException { // ✅ explicit name
        bedService.delete(bedId);
        return ResponseEntity.ok("Bed deleted successfully");
    }

    @PostMapping("/{bedId}/assign")
    public ResponseEntity<BedResponseDTO> assignPatient(
            @PathVariable("bedId") int bedId, // ✅ explicit name
            @RequestBody Map<String, Object> body) throws BedNotFoundException {
        int patientId = (Integer) body.get("patientId");
        Bed bed = bedService.assignPatientToBed(bedId, patientId);
        BedResponseDTO dto = new BedResponseDTO();
        dto.setBed(bed);
        dto.setStatusCode(200);
        dto.setMessage("Patient assigned to bed " + bedId + " successfully");
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{bedId}/discharge")
    public ResponseEntity<BedResponseDTO> dischargePatient(
            @PathVariable("bedId") int bedId) throws BedNotFoundException { // ✅ explicit name
        Bed bed = bedService.dischargePatient(bedId);
        BedResponseDTO dto = new BedResponseDTO();
        dto.setBed(bed);
        dto.setStatusCode(200);
        dto.setMessage("Patient discharged and bed " + bedId + " is now available");
        return ResponseEntity.ok(dto);
    }

    // ✅ Fixed: returns ResponseEntity for consistency
    @GetMapping("/getAllBedsPaginated")
    public ResponseEntity<Page<Bed>> getAllBedsPaginated(
            @RequestParam("pgno") int pgno,          // ✅ explicit name
            @RequestParam("size") int size,           // ✅ explicit name
            @RequestParam("sorting") String sorting,  // ✅ explicit name
            @RequestParam("asc") boolean asc) {       // ✅ explicit name
        Sort sort = asc ? Sort.by(sorting).ascending() : Sort.by(sorting).descending();
        Pageable pageable = PageRequest.of(pgno, size, sort);
        return ResponseEntity.ok(this.bedService.getAllBedsWithPaginated(pageable));
    }
}