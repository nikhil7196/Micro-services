package com.usermicroservice.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	
	private String userName;
	private String role;
	@Email(message="Invalid email Format")
	@NotBlank(message="Email is Requrired")
	private String email;
	private String phoneNumber;
	private String password;
	
	
	private String status="PENDING";
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User(String userName, String userRole, String userEmail, String userPhone,String password,String status) {
		super();
		this.userName = userName;
		this.role = userRole;
		this.email = userEmail;
		this.phoneNumber = userPhone;
		this.password=password;
		this.status=status;
				
	}
	public User() {
		super();
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRole() {
		return role;
	}
	public void setUserRole(String userRole) {
		this.role = userRole;
	}
	public String getUserEmail() {
		return email;
	}
	public void setUserEmail(String userEmail) {
		this.email = userEmail;
	}
	public String getUserPhone() {
		return phoneNumber;
	}
	public void setUserPhone(String userPhone) {
		this.phoneNumber = userPhone;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == null || getClass() != o.getClass()) return false;
	    User user = (User) o;
	    return userId == user.userId;
	}

	@Override
	public int hashCode() { return Objects.hash(userId); }
	
	
}
