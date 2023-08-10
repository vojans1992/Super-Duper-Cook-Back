package com.tim1.cook.service;

import java.util.List;

import com.tim1.cook.entities.AllergenEntity;
import com.tim1.cook.entities.UserEntity;
import com.tim1.cook.entities.dto.UserDTO;

public interface UserService {

	public Iterable<UserEntity> getAllUsers();

	public UserEntity createUser(UserEntity newUser);

	public UserEntity updateUser(Integer id, UserEntity updatedUser);

	public void deleteUser(Integer id);

	public UserEntity addRoleToUser(Integer id, Integer roleId);

	public UserDTO updateUserDTO(Integer id, UserDTO dto);

	public UserEntity findByUsername(String username);

	public UserEntity addAllergenToUser(List<AllergenEntity> listOfAllergens, String username);
}
