
package com.medi360.DTO;

import com.medi360.entities.Bed;

public class BedResponseDTO {
 private Bed bed;
 private int statusCode;
 private String message;
 public Bed getBed() {
	return bed;
 }
 public void setBed(Bed bed) {
	this.bed = bed;
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
