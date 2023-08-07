package com.tim1.cook.entities.dto;

public class LoginDTO {

	private String user;
	private String token;

	public LoginDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginDTO(String user, String token) {
		super();
		this.user = user;
		this.token = token;
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

}
