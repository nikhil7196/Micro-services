package com.medi360.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medi360.DTO.ComplianceReportDTO;
import com.medi360.DTO.ComplianceReportResponseDTO;
import com.medi360.entities.ComplianceReport;
import com.medi360.service.ComplianceReportService;

@RestController
@RequestMapping("/api/compliance-reports")
public class ComplianceReportController {

    private final ComplianceReportService complianceReportService;

    public ComplianceReportController(ComplianceReportService complianceReportService) {
        this.complianceReportService = complianceReportService;
    }

    // ✅ ADD
    @PostMapping("/addComplianceReport")
    public ResponseEntity<ComplianceReportResponseDTO> addComplianceReport(
            @RequestBody ComplianceReportDTO complianceReportDTO) {

        ComplianceReport report = complianceReportService.addComplianceReport(
                complianceReportDTO.getComplianceReport());

        return ResponseEntity.status(201).body(buildResponse(report, 201,
                "Compliance report created successfully"));
    }

    // ✅ UPDATE
    @PutMapping("/updateComplianceReport")
    public ResponseEntity<ComplianceReportResponseDTO> updateComplianceReport(
            @RequestBody ComplianceReportDTO complianceReportDTO) {

        ComplianceReport report = complianceReportService.updateComplianceReport(
                complianceReportDTO.getComplianceReport());

        return ResponseEntity.ok(buildResponse(report, 200,
                "Compliance report updated successfully"));
    }

    // ✅ DELETE — explicit PathVariable name
    @DeleteMapping("/deleteComplianceReport/{id}")
    public ResponseEntity<String> deleteComplianceReport(
            @PathVariable("id") int id) {
        return ResponseEntity.ok(complianceReportService.deleteComplianceReport(id));
    }

    // ✅ GET ALL
    @GetMapping("/fetchAllComplianceReports")
    public ResponseEntity<List<ComplianceReport>> fetchAllComplianceReports() {
        return ResponseEntity.ok(complianceReportService.getAllComplianceReports());
    }

    // ✅ GET BY ID — explicit PathVariable name
    @GetMapping("/{id}")
    public ResponseEntity<ComplianceReport> getComplianceReportById(
            @PathVariable("id") int id) {
        return ResponseEntity.ok(complianceReportService.getComplianceReportById(id));
    }

    // ✅ PAGINATED — explicit RequestParam names
    @GetMapping("/paginated")
    public ResponseEntity<Page<ComplianceReport>> getAllComplianceReportsPaginated(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "sortBy", defaultValue = "reportId") String sortBy,
            @RequestParam(name = "asc", defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                complianceReportService.getComplianceReportsWithPagination(pageable));
    }

    // ✅ Helper
    private ComplianceReportResponseDTO buildResponse(ComplianceReport report,
                                                       int statusCode, String message) {
        ComplianceReportResponseDTO response = new ComplianceReportResponseDTO();
        response.setReportId(report.getReportId());
        response.setReportScope(report.getReportScope());
        response.setReportMetrics(report.getReportMetrics());
        response.setReportGeneratedDate(report.getReportGeneratedDate());
        response.setStatusCode(statusCode);
        response.setMessage(message);

        if (report.getKpiReports() != null) {
            response.setKpiReportIds(
                    report.getKpiReports()
                          .stream()
                          .map(kpi -> kpi.getKpiId())
                          .collect(Collectors.toList())
            );
        }

        return response;
    }
}