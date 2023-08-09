package com.tim1.cook.entities.dto;

public class LoginDTO {

	private String user;
	private String token;
	private String role;

	public LoginDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginDTO(String user, String token, String role) {
		super();
		this.user = user;
		this.token = token;
		this.role = role;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
}
