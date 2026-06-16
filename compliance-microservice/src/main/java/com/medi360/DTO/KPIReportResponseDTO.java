package com.medi360.DTO;

import java.time.LocalDate;

public class KPIReportResponseDTO {

    private int kpiId;
    private String kpiReportScope;
    private String kpiMetrics;
    private LocalDate kpiGeneratedDate;

    // The compliance report this KPI belongs to
    private Integer complianceReportId;

    private int statusCode;
    private String message;

    public KPIReportResponseDTO() {}

    // ── Getters & Setters ──────────────────────────────────────────────────────

    public int getKpiId() { return kpiId; }
    public void setKpiId(int kpiId) { this.kpiId = kpiId; }

    public String getKpiReportScope() { return kpiReportScope; }
    public void setKpiReportScope(String kpiReportScope) { this.kpiReportScope = kpiReportScope; }

    public String getKpiMetrics() { return kpiMetrics; }
    public void setKpiMetrics(String kpiMetrics) { this.kpiMetrics = kpiMetrics; }

    public LocalDate getKpiGeneratedDate() { return kpiGeneratedDate; }
    public void setKpiGeneratedDate(LocalDate kpiGeneratedDate) { this.kpiGeneratedDate = kpiGeneratedDate; }

    public Integer getComplianceReportId() { return complianceReportId; }
    public void setComplianceReportId(Integer complianceReportId) { this.complianceReportId = complianceReportId; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
