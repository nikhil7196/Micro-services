package com.auditlog.entities;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auditId;

    private String action;
    private LocalDateTime timestamp;
    private int userId;

    public AuditLog(String action, LocalDateTime timestamp, int userId) {
        super();
        this.action = action;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public AuditLog() { super(); }

    public int getAuditId() { return auditId; }
    public void setAuditId(int auditId) { this.auditId = auditId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public int getUserId() { return userId; }

    
    public void setUserId(int userId) { this.userId = userId; }
}