package com.tim1.cook.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.tim1.cook.entities.RoleEntity;

public class UserDTO {

	@NotNull(message = "Username must be provided.")
	@Column(name = "username", unique = true, length = 20)
	@Size(min = 5, max = 20, message = "Username name must be between {min} and {max} characters long.")
	private String username;
	@NotNull(message = "Password must be provided.")
	@Size(min = 5, max = 20, message = "Password name must be between {min} and {max} characters long.")
	private String password;
	@NotNull(message = "Password must be provided.")
	@Size(min = 5, max = 20, message = "Password name must be between {min} and {max} characters long.")
	private String repeatedPassword;
	private String token;
	private RoleEntity role;

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

}
