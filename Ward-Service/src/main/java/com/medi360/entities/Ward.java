
package com.medi360.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Ward {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer wardId;
	private String wardname;
	private int wardcapacity;
	private String wardstatus;
	
	@OneToMany(mappedBy="ward", cascade=CascadeType.ALL)
	@JsonIgnoreProperties("ward")
	private List<Bed> beds;
	public List<Bed> getBeds() {
	    return beds;
	}

	public void setBeds(List<Bed> beds) {
	    this.beds = beds;
	}
	public Integer getWardId() {
		return wardId;
	}
	public void setWardId(Integer wardId) {
		this.wardId = wardId;
	}
	public String getWardname() {
		return wardname;
	}
	public void setWardname(String wardname) {
		this.wardname = wardname;
	}
	public int getWardcapacity() {
		return wardcapacity;
	}
	public void setWardcapacity(int wardcapacity) {
		this.wardcapacity = wardcapacity;
	}
	public String getWardstatus() {
		return wardstatus;
	}
	public void setWardstatus(String wardstatus) {
		this.wardstatus = wardstatus;
	}
	public Ward(String wardname, int wardcapacity, String wardstatus) {
		super();
		this.wardname = wardname;
		this.wardcapacity = wardcapacity;
		this.wardstatus = wardstatus;
	}
	public Ward() {
		super();
	}
	
	
	
}
