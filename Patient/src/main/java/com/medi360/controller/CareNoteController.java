package com.medi360.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.medi360.DTO.CareNoteDTO;
import com.medi360.DTO.CareNoteResponseDTO;
import com.medi360.service.CareNoteService;
import java.util.List;

@RestController
@RequestMapping("/api/care-notes")
// ✅ Removed @CrossOrigin — API Gateway handles CORS
public class CareNoteController {

    private final CareNoteService careNoteService;

    public CareNoteController(CareNoteService careNoteService) {
        this.careNoteService = careNoteService;
    }

    @PostMapping("/add")
    public ResponseEntity<CareNoteResponseDTO> addCareNote(
            @RequestBody CareNoteDTO request) {
        return ResponseEntity.ok(careNoteService.addCareNote(request));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<CareNoteResponseDTO>> getByPatient(
            @PathVariable("patientId") int patientId) { // ✅ explicit name
        return ResponseEntity.ok(careNoteService.getCareNotesByPatient(patientId));
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<CareNoteResponseDTO> getById(
            @PathVariable("noteId") Long noteId) { // ✅ explicit name
        return ResponseEntity.ok(careNoteService.getCareNoteById(noteId));
    }
}