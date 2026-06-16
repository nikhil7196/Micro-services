package com.auditlog.exception;

public class AuditNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public AuditNotFoundException() {
		super();
	}
	
	public AuditNotFoundException(String message) {
		super(message);
	}
	
	public AuditNotFoundException(String message,Throwable thr) {
		super(message,thr);
	}
}
