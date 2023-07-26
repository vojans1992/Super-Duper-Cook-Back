package com.tim1.cook.service;

import com.tim1.cook.entities.UserEntity;

public interface UserService {

	public UserEntity createUser(UserEntity user);
	public UserEntity updateUser(Integer id, UserEntity updatedUser);
	public UserEntity addRoleToUser(Integer id, Integer roleId);
	
}

