package com.medi360.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medi360.DTO.WardDTO;
import com.medi360.DTO.WardResponseDTO;
import com.medi360.entities.Ward;
import com.medi360.exception.WardNotFoundException;
import com.medi360.service.WardService;

@RestController
@RequestMapping("/api/ward")
public class WardController {

    @Autowired
    private WardService wardService;

    @PostMapping("/create")
    public ResponseEntity<WardResponseDTO> createWard(@RequestBody WardDTO wardDTO) {
        Ward ward = wardService.createWard(wardDTO.getWard());
        WardResponseDTO response = new WardResponseDTO();
        response.setWard(ward);
        response.setStatusCode(201);
        response.setMessage("Ward created successfully");
        return ResponseEntity.status(201).body(response);
    }

    // ✅ Fixed: was returning 201 for a GET — changed to 200
    @GetMapping("/getAllWards")
    public ResponseEntity<List<Ward>> getAllWard() {
        return ResponseEntity.ok(wardService.getAllWard());
    }

    @GetMapping("/getWard/{wardId}")
    public ResponseEntity<WardResponseDTO> getWardById(
            @PathVariable("wardId") int wardId) { // ✅ explicit name
        Ward ward = wardService.getWardById(wardId);
        WardResponseDTO dto = new WardResponseDTO();
        dto.setWard(ward);
        dto.setStatusCode(200);
        dto.setMessage("Found ward with Id: " + wardId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/updateWard")
    public ResponseEntity<WardResponseDTO> updateWard(
            @RequestBody WardDTO wardDTO) throws WardNotFoundException {
        Ward ward = wardService.updateWard(wardDTO.getWard());
        WardResponseDTO response = new WardResponseDTO();
        response.setWard(ward);
        response.setStatusCode(200);
        response.setMessage("Ward updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteWard/{wardId}")
    public ResponseEntity<String> deleteWard(
            @PathVariable("wardId") int wardId) throws WardNotFoundException { // ✅ explicit name
        wardService.deleteWard(wardId);
        return ResponseEntity.ok("Ward deleted successfully");
    }

    @GetMapping("/{wardId}/occupancy-report")
    public ResponseEntity<String> getOccupancyReport(
            @PathVariable("wardId") int wardId) throws WardNotFoundException { // ✅ explicit name
        String report = wardService.getWardOccupancyReport(wardId);
        return ResponseEntity.ok(report);
    }

    // ✅ Fixed: returns ResponseEntity for consistency
    @GetMapping("/getAllWardsPaginated")
    public ResponseEntity<Page<Ward>> getAllWardsPaginated(
            @RequestParam("pgno") int pgno,          // ✅ explicit name
            @RequestParam("size") int size,           // ✅ explicit name
            @RequestParam("sorting") String sorting,  // ✅ explicit name
            @RequestParam("asc") boolean asc) {       // ✅ explicit name
        Sort sort = asc ? Sort.by(sorting).ascending() : Sort.by(sorting).descending();
        Pageable pageable = PageRequest.of(pgno, size, sort);
        return ResponseEntity.ok(this.wardService.getAllWardsWithPaginated(pageable));
    }
}