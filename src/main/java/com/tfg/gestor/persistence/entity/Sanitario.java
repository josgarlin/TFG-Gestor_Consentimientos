package com.tfg.gestor.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sanitario")
public class Sanitario {

	@Id
	@Column(length = 9)
	private String dni;
	private String username;
	private String password;
	private String name;
	private SanitarioStatus status = SanitarioStatus.OUTSIDE;
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SanitarioStatus getStatus() {
		return status;
	}
	public void setStatus(SanitarioStatus status) {
		this.status = status;
	}
	
}
