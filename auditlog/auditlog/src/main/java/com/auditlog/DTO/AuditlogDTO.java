package com.auditlog.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuditlogDTO {
	
	@NotNull(message="User ID is required")
	private int userId;
	@NotBlank(message="Action is required")
	private String action;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	
	
}
