package com.medi360.DTO;

import java.time.LocalDate;
import java.util.List;

public class ComplianceReportResponseDTO {

    private int reportId;
    private String reportScope;
    private String reportMetrics;
    private LocalDate reportGeneratedDate;

     private List<Integer> kpiReportIds;

    private int statusCode;
    private String message;

 
    public int getReportId() { return reportId; }
    public void setReportId(int reportId) { this.reportId = reportId; }

    public String getReportScope() { return reportScope; }
    public void setReportScope(String reportScope) { this.reportScope = reportScope; }

    public String getReportMetrics() { return reportMetrics; }
    public void setReportMetrics(String reportMetrics) { this.reportMetrics = reportMetrics; }

    public LocalDate getReportGeneratedDate() { return reportGeneratedDate; }
    public void setReportGeneratedDate(LocalDate reportGeneratedDate) { this.reportGeneratedDate = reportGeneratedDate; }

    public List<Integer> getKpiReportIds() { return kpiReportIds; }
    public void setKpiReportIds(List<Integer> kpiReportIds) { this.kpiReportIds = kpiReportIds; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
