package com.medi360.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.medi360.DTO.InvoiceWithPatientDTO;
import com.medi360.DTO.PatientDTO;
import com.medi360.DTO.PatientResponseDTO;
import com.medi360.client.PatientClient;
import com.medi360.db.InvoiceRepository;
import com.medi360.entities.Invoice;
import com.medi360.exception.InvoiceNotFoundException;
import com.medi360.exception.PatientNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PatientClient patientClient;

    // ✅ ADD INVOICE
    public Invoice addInvoice(Invoice invoice) throws PatientNotFoundException {
        int patientId = invoice.getPatient().getPatientId();
        validatePatient(patientId);
        return invoiceRepository.save(invoice);
    }

    // ✅ GET BY ID WITH PATIENT — used by controller
    public InvoiceWithPatientDTO getInvoiceByIdWithPatient(int id) throws InvoiceNotFoundException {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with id " + id));
        return mapToDTO(invoice);
    }

    // ✅ UPDATE INVOICE
    @Transactional
    public Invoice updateInvoice(Invoice incoming)
            throws InvoiceNotFoundException, PatientNotFoundException {

        Invoice existing = invoiceRepository.findById(incoming.getInvoiceId())
                .orElseThrow(() -> new InvoiceNotFoundException(
                        "Invoice not found with id " + incoming.getInvoiceId()));

        validatePatient(incoming.getPatient().getPatientId());

        existing.setPatient(incoming.getPatient());
        existing.setAmount(incoming.getAmount());
        existing.setInvoiceDate(incoming.getInvoiceDate());
        existing.setPaymentStatus(incoming.getPaymentStatus());
        // ✅ Removed: paymentMode, adjustmentAmount, refundStatus

        return invoiceRepository.save(existing);
    }

    // ✅ DELETE
    public String deleteInvoice(int id) throws InvoiceNotFoundException {
        if (!invoiceRepository.existsById(id)) {
            throw new InvoiceNotFoundException("Invoice not found with id " + id);
        }
        invoiceRepository.deleteById(id);
        return "Successfully deleted invoice";
    }

    // ✅ GET ALL WITH PATIENT
    public List<InvoiceWithPatientDTO> getAllInvoicesWithPatient() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ PAGINATED WITH PATIENT
    public Page<InvoiceWithPatientDTO> getAllInvoicesWithPatientPaginated(Pageable pageable) {
        return invoiceRepository.findAll(pageable).map(this::mapToDTO);
    }

    // ✅ GET BY PATIENT
    public List<Invoice> getInvoicesByPatient(int patientId) {
        return invoiceRepository.findByPatient_PatientId(patientId);
    }

    // ✅ GET BY STATUS
    public List<Invoice> getInvoicesByPaymentStatus(String paymentStatus) {
        return invoiceRepository.findByPaymentStatus(paymentStatus);
    }

    // ✅ Helper — validate patient via Feign
    private void validatePatient(int patientId) throws PatientNotFoundException {
        try {
            PatientResponseDTO response = patientClient.getPatientById(patientId);
            if (response == null || response.getPatient() == null) {
                throw new PatientNotFoundException("Patient not found with id " + patientId);
            }
        } catch (PatientNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new PatientNotFoundException("Patient not found with id " + patientId);
        }
    }

    // ✅ Helper — map Invoice to InvoiceWithPatientDTO
    private InvoiceWithPatientDTO mapToDTO(Invoice invoice) {
        InvoiceWithPatientDTO dto = new InvoiceWithPatientDTO();
        dto.setInvoiceId(invoice.getInvoiceId());
        dto.setAmount(invoice.getAmount());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setPaymentStatus(invoice.getPaymentStatus());
        // ✅ Removed: paymentMode, adjustmentAmount, refundStatus

        int patientId = invoice.getPatient().getPatientId();
        try {
            PatientResponseDTO response = patientClient.getPatientById(patientId);
            if (response != null && response.getPatient() != null) {
                dto.setPatient(response.getPatient());
            } else {
                dto.setPatient(fallbackPatient(patientId));
            }
        } catch (Exception e) {
            dto.setPatient(fallbackPatient(patientId));
        }

        return dto;
    }

    // ✅ Helper — fallback when Patient Service is unreachable
    private PatientDTO fallbackPatient(int patientId) {
        PatientDTO fallback = new PatientDTO();
        fallback.setPatientId(patientId);
        fallback.setPatientName("Unknown");
        return fallback;
    }
}