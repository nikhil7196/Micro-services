package com.notification.exception;

public class NotificationNotfoundException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public NotificationNotfoundException() {
		super();
	}
	
	public NotificationNotfoundException(String message) {
		super(message);
	}
	
	public NotificationNotfoundException(String message,Throwable thr) {
		super(message,thr);
	}
}
