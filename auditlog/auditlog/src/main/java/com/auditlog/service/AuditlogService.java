package com.auditlog.service;

import com.auditlog.DTO.AuditlogResponseDTO;
import com.auditlog.db.AuditlogRepository;
import com.auditlog.entities.AuditLog;
import com.auditlog.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditlogService {

    @Autowired
    private AuditlogRepository auditlogrepo;

    public void log(String action,int id) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        log.setUserId(id);
        auditlogrepo.save(log);
    }

    public void logFailure(String action, String errorMessage) {
        log(action + "_FAILED | Error: " + errorMessage,0);
    }

    public List<AuditlogResponseDTO> getAllAuditlog() {
        return auditlogrepo.findAll()
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Page<AuditLog> getAllAuditlogsWithPagination(Pageable pageable) {
        return auditlogrepo.findAll(pageable);
    }

    public AuditlogResponseDTO findById(int id) {
        AuditLog a = auditlogrepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit log not found with ID: " + id));
        return mapToDTO(a);
    }

    private AuditlogResponseDTO mapToDTO(AuditLog a) {
        AuditlogResponseDTO dto = new AuditlogResponseDTO();
        dto.setAuditId(a.getAuditId());
        dto.setAction(a.getAction());
        dto.setTimestamp(a.getTimestamp());
        dto.setUserId(a.getUserId());
        return dto;
    }
}