package com.medi360.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name = "compliance_reports")
public class ComplianceReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;

    private String reportScope;

    private String reportMetrics;

  
    private LocalDate reportGeneratedDate;

    
    @Transient
    private String kpiCategory;

    @OneToMany(
        mappedBy = "complianceReport",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<KPIReport> kpiReports = new ArrayList<>();

    public ComplianceReport() {}

 
    public int getReportId() { return reportId; }
    public void setReportId(int reportId) { this.reportId = reportId; }

    public String getReportScope() { return reportScope; }
    public void setReportScope(String reportScope) { this.reportScope = reportScope; }

    public String getReportMetrics() { return reportMetrics; }
    public void setReportMetrics(String reportMetrics) { this.reportMetrics = reportMetrics; }

    public LocalDate getReportGeneratedDate() { return reportGeneratedDate; }
    public void setReportGeneratedDate(LocalDate reportGeneratedDate) { this.reportGeneratedDate = reportGeneratedDate; }

    public String getKpiCategory() { return kpiCategory; }
    public void setKpiCategory(String kpiCategory) { this.kpiCategory = kpiCategory; }

    public List<KPIReport> getKpiReports() { return kpiReports; }
    public void setKpiReports(List<KPIReport> kpiReports) { this.kpiReports = kpiReports; }

 
    public void addKpiReport(KPIReport kpiReport) {
        kpiReports.add(kpiReport);
        kpiReport.setComplianceReport(this);
    }

    public void removeKpiReport(KPIReport kpiReport) {
        kpiReports.remove(kpiReport);
        kpiReport.setComplianceReport(null);
    }
}
