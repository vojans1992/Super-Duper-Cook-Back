package com.tim1.cook.service;

import com.tim1.cook.entities.UserEntity;
import com.tim1.cook.entities.dto.UserDTO;

public interface UserService {

	public Iterable<UserEntity> getAllUsers();

	public UserEntity createUser(UserEntity newUser);

	public UserEntity updateUser(Integer id, UserEntity updatedUser);

	public void deleteUser(Integer id);

	public UserEntity addRoleToUser(Integer id, Integer roleId);

	public UserDTO updateUserDTO(Integer id, UserDTO dto);

}
