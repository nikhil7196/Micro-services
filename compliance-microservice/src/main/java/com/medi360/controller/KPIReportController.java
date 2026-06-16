package com.medi360.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medi360.DTO.KPIReportDTO;
import com.medi360.DTO.KPIReportResponseDTO;
import com.medi360.entities.KPIReport;
import com.medi360.service.KPIReportService;

@RestController
@RequestMapping("/api/kpi-report")
public class KPIReportController {

    @Autowired
    private KPIReportService kpiReportService;

     @PostMapping("/addKPIReport")
    public ResponseEntity<KPIReportResponseDTO> addKPIReport(
            @RequestBody KPIReportDTO kpiReportDTO) {

        KPIReport report = kpiReportService.addKPIReport(kpiReportDTO.getKpiReport());
        return ResponseEntity.status(201).body(toDTO(report));
    }

     @GetMapping("/fetchAllKPIReports")
    public ResponseEntity<List<KPIReportResponseDTO>> fetchAllKPIReports() {
        List<KPIReportResponseDTO> list = kpiReportService.getAllKPIReports()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

     @GetMapping("/fetchAllKPIReports/paginated")
    public ResponseEntity<Page<KPIReportResponseDTO>> fetchAllKPIReportsPaginated(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "sortBy", defaultValue = "kpiId") String sortBy,
            @RequestParam(name = "asc", defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<KPIReport> kpiPage = kpiReportService.getKPIReportsWithPagination(pageable);

        List<KPIReportResponseDTO> dtoList = kpiPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(dtoList, pageable, kpiPage.getTotalElements()));
    }

     private KPIReportResponseDTO toDTO(KPIReport report) {
        KPIReportResponseDTO dto = new KPIReportResponseDTO();
        dto.setKpiId(report.getKpiId());
        dto.setKpiReportScope(report.getKpiReportScope());
        dto.setKpiMetrics(report.getKpiMetrics());
        dto.setKpiGeneratedDate(report.getKpiGeneratedDate());
        dto.setComplianceReportId(
                report.getComplianceReport() != null
                        ? report.getComplianceReport().getReportId()
                        : null
        );
        dto.setStatusCode(200);
        dto.setMessage("Success");
        return dto;
    }
}