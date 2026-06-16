package com.medi360.DTO;

import com.medi360.entities.InsuranceClaim;

import jakarta.validation.Valid;

public class InsuranceClaimDTO {
	
	@Valid
	private InsuranceClaim insuranceClaim;

	public InsuranceClaim getInsuranceClaim() {
		return insuranceClaim;
	}

	public void setInsuranceClaim(InsuranceClaim insuranceClaim) {
		this.insuranceClaim = insuranceClaim;
	}
	
}
