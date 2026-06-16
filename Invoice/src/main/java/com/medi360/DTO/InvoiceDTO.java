package com.medi360.DTO;

import com.medi360.entities.Invoice;

import jakarta.validation.Valid;

public class InvoiceDTO {
	
	@Valid
	private Invoice invoice;

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	
}
