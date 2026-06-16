package com.medi360.entities;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "kpi_reports")
public class KPIReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kpiId;

    private String kpiReportScope;

  
    private String kpiMetrics;


    private LocalDate kpiGeneratedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private ComplianceReport complianceReport;

    public KPIReport() {}

    // ── Getters & Setters ──────────────────────────────────────────────────────

    public int getKpiId() { return kpiId; }
    public void setKpiId(int kpiId) { this.kpiId = kpiId; }

    public String getKpiReportScope() { return kpiReportScope; }
    public void setKpiReportScope(String kpiReportScope) { this.kpiReportScope = kpiReportScope; }

    public String getKpiMetrics() { return kpiMetrics; }
    public void setKpiMetrics(String kpiMetrics) { this.kpiMetrics = kpiMetrics; }

    public LocalDate getKpiGeneratedDate() { return kpiGeneratedDate; }
    public void setKpiGeneratedDate(LocalDate kpiGeneratedDate) { this.kpiGeneratedDate = kpiGeneratedDate; }

    public ComplianceReport getComplianceReport() { return complianceReport; }
    public void setComplianceReport(ComplianceReport complianceReport) { this.complianceReport = complianceReport; }
}
