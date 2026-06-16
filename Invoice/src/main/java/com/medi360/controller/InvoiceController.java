package com.medi360.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medi360.DTO.InvoiceDTO;
import com.medi360.DTO.InvoiceResponseDTO;
import com.medi360.DTO.InvoiceWithPatientDTO;
import com.medi360.entities.Invoice;
import com.medi360.exception.InvoiceNotFoundException;
import com.medi360.exception.PatientNotFoundException;
import com.medi360.service.InvoiceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;
    @PostMapping("/addInvoice")
    public ResponseEntity<InvoiceResponseDTO> addInvoice(
            @Valid @RequestBody InvoiceDTO invoiceDTO) throws PatientNotFoundException {
        Invoice i = this.invoiceService.addInvoice(invoiceDTO.getInvoice());
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setInvoice(i);
        dto.setStatusCode(201);
        dto.setMessage("Invoice created successfully");
        return ResponseEntity.status(201).body(dto);
    }
    @GetMapping("/getInvoiceById/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(
            @PathVariable("id") int id) throws InvoiceNotFoundException {
        InvoiceWithPatientDTO invoiceWithPatient = invoiceService.getInvoiceByIdWithPatient(id);
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setInvoice(invoiceWithPatient);
        dto.setStatusCode(200);
        dto.setMessage("Invoice record retrieved successfully");
        return ResponseEntity.ok(dto);
    }
    @PutMapping("/updateInvoice")
    public ResponseEntity<InvoiceResponseDTO> updateInvoice(
            @Valid @RequestBody InvoiceDTO invoiceDTO)
            throws InvoiceNotFoundException, PatientNotFoundException {
        Invoice i = this.invoiceService.updateInvoice(invoiceDTO.getInvoice());
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setInvoice(i);
        dto.setStatusCode(200);
        dto.setMessage("Invoice updated successfully");
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("/deleteInvoice/{id}")
    public ResponseEntity<String> deleteInvoice(
            @PathVariable("id") int id) throws InvoiceNotFoundException {
        return ResponseEntity.ok(this.invoiceService.deleteInvoice(id));
    }
    @GetMapping("/fetchAllInvoices")
    public ResponseEntity<List<InvoiceWithPatientDTO>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoicesWithPatient());
    }
    @GetMapping("/fetchAllInvoicesPaginated")
    public ResponseEntity<Page<InvoiceWithPatientDTO>> getAllInvoicesPaginated(
            @RequestParam("pgno") int pgno,
            @RequestParam("size") int size,
            @RequestParam("sorting") String sorting,
            @RequestParam("asc") boolean asc) {
        Sort sort = asc ? Sort.by(sorting).ascending() : Sort.by(sorting).descending();
        Pageable pageable = PageRequest.of(pgno, size, sort);
        return ResponseEntity.ok(invoiceService.getAllInvoicesWithPatientPaginated(pageable));
    }
    @GetMapping("/getInvoicesByPatient/{patientId}")
    public ResponseEntity<List<Invoice>> getInvoicesByPatient(
            @PathVariable("patientId") int patientId) {
        return ResponseEntity.ok(invoiceService.getInvoicesByPatient(patientId));
    }
    @GetMapping("/getInvoicesByStatus/{status}")
    public ResponseEntity<List<Invoice>> getInvoicesByStatus(
            @PathVariable("status") String status) {
        return ResponseEntity.ok(invoiceService.getInvoicesByPaymentStatus(status));
    }
}