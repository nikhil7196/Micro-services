package com.medi360.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.medi360.db.KPIReportRepository;
import com.medi360.db.ComplianceReportRepository;
import com.medi360.entities.ComplianceReport;
import com.medi360.entities.KPIReport;

@Service
public class KPIReportService {

    private final KPIReportRepository kpiReportRepository;
    private final ComplianceReportRepository complianceReportRepository;

    public KPIReportService(KPIReportRepository kpiReportRepository,
                            ComplianceReportRepository complianceReportRepository) {
        this.kpiReportRepository = kpiReportRepository;
        this.complianceReportRepository = complianceReportRepository;
    }

    public KPIReport addKPIReport(KPIReport kpiReport) {

         if (kpiReport.getComplianceReport() == null) {
            throw new RuntimeException("ComplianceReport must be provided");
        }

        int reportId = kpiReport.getComplianceReport().getReportId();

         ComplianceReport complianceReport =
                complianceReportRepository.findById(reportId)
                        .orElseThrow(() ->
                                new RuntimeException("ComplianceReport not found with id: " + reportId));

        kpiReport.setComplianceReport(complianceReport);

        return kpiReportRepository.save(kpiReport);
    }

    public List<KPIReport> getAllKPIReports() {
        return kpiReportRepository.findAll();
    }

    public Page<KPIReport> getKPIReportsWithPagination(Pageable pageable) {
        return kpiReportRepository.findAll(pageable);
    }

    public String deleteKPIReport(int id) {
        kpiReportRepository.deleteById(id);
        return "KPI report deleted successfully";
    }
}
