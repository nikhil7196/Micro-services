
package com.medi360.DTO;

import com.medi360.entities.Ward;

public class WardResponseDTO {
private Ward ward;
private int statusCode;
private String message;
public Ward getWard() {
	return ward;
}
public void setWard(Ward ward) {
	this.ward = ward;
}
public int getStatusCode() {
	return statusCode;
}
public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
}
