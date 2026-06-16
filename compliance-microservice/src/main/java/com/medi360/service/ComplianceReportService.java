package com.medi360.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medi360.db.ComplianceReportRepository;
import com.medi360.db.KPIReportRepository;
import com.medi360.entities.ComplianceReport;
import com.medi360.entities.KPIReport;

@Service
public class ComplianceReportService {

    private final ComplianceReportRepository complianceReportRepository;
    private final KPIReportRepository kpiReportRepository;

    private static final Map<String, String[]> KPI_CATEGORIES = Map.of(
        "OCCUPANCY_RATE",             new String[]{"Occupancy Rate",             "Bed occupancy percentage across wards"},
        "APPOINTMENT_FULFILLMENT",    new String[]{"Appointment Fulfillment",    "Percentage of appointments completed vs scheduled"},
        "CLAIM_SUCCESS_RATE",         new String[]{"Claim Success Rate",         "Insurance claims approved vs submitted"},
        "PATIENT_REGISTRATION_RATE",  new String[]{"Patient Registration Rate",  "New patients registered per day"},
        "BILLING_COLLECTION_RATE",    new String[]{"Billing Collection Rate",    "Invoices paid vs total invoices raised"},
        "DISCHARGE_TURNAROUND_TIME",  new String[]{"Discharge Turnaround Time",  "Average time from discharge order to bed release"},
        "AUDIT_COMPLIANCE_SCORE",     new String[]{"Audit Compliance Score",     "Audit actions flagged vs total actions logged"},
        "NOTIFICATION_RESPONSE_RATE", new String[]{"Notification Response Rate", "Alerts acknowledged vs total alerts sent"}
    );

    public ComplianceReportService(ComplianceReportRepository complianceReportRepository,
                                   KPIReportRepository kpiReportRepository) {
        this.complianceReportRepository = complianceReportRepository;
        this.kpiReportRepository = kpiReportRepository;
    }

    @Transactional
    public ComplianceReport addComplianceReport(ComplianceReport report) {
        String selectedCategory = report.getKpiCategory();
        ComplianceReport saved = complianceReportRepository.save(report);

        if (selectedCategory != null && KPI_CATEGORIES.containsKey(selectedCategory)) {
            String[] cat = KPI_CATEGORIES.get(selectedCategory);

            KPIReport kpi = new KPIReport();
            kpi.setKpiReportScope(cat[0]);
            kpi.setKpiMetrics(cat[1]);
            kpi.setKpiGeneratedDate(saved.getReportGeneratedDate());
            kpi.setComplianceReport(saved);

            KPIReport savedKpi = kpiReportRepository.save(kpi);
            saved.getKpiReports().add(savedKpi);
        }

        return saved;
    }

    public ComplianceReport updateComplianceReport(ComplianceReport report) {
        if (!complianceReportRepository.existsById(report.getReportId())) {
            throw new RuntimeException("ComplianceReport not found with id: " + report.getReportId());
        }
        return complianceReportRepository.save(report);
    }

    public ComplianceReport getComplianceReportById(int id) {
        return complianceReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ComplianceReport not found with id: " + id));
    }

    public List<ComplianceReport> getAllComplianceReports() {
        return complianceReportRepository.findAll();
    }

    public String deleteComplianceReport(int id) {
        if (!complianceReportRepository.existsById(id)) {
            throw new RuntimeException("ComplianceReport not found with id: " + id);
        }
        complianceReportRepository.deleteById(id);
        return "Compliance report deleted successfully";
    }

    public Page<ComplianceReport> getComplianceReportsWithPagination(Pageable pageable) {
        return complianceReportRepository.findAll(pageable);
    }
}
