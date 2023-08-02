package com.tim1.cook.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim1.cook.entities.AdminEntity;
import com.tim1.cook.entities.RoleEntity;
import com.tim1.cook.entities.UserEntity;
import com.tim1.cook.repositories.RoleRepository;
import com.tim1.cook.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserEntity createUser(UserEntity newUser) {
		if (newUser.getUsername() == null || newUser.getUsername().isEmpty() || newUser.getPassword() == null
				|| newUser.getPassword().isEmpty()) {
			throw new IllegalArgumentException("Username and password are required fields.");
		}

		if (userRepository.existsByUsername(newUser.getUsername())) {
			throw new IllegalArgumentException("Username already exists.");
		}

		userRepository.save(newUser);
		return newUser;
	}

	@Override
	public UserEntity updateUser(Integer id, UserEntity updatedUser) {
		UserEntity user = userRepository.findById(id).get();
		if (user == null) {
			throw new NoSuchElementException("User with ID " + id + " not found.");
		}
		user.setUsername(updatedUser.getUsername());
		user.setPassword(updatedUser.getPassword());
		userRepository.save(user);
		return user;
	}

	@Override
	public UserEntity addRoleToUser(Integer userId, Integer roleId) {
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User with ID " + userId + " not found."));
		RoleEntity role = roleRepository.findById(roleId).orElseThrow(() -> new NoSuchElementException("Role with ID " + roleId + " not found."));

		user.setRole(role);

		return userRepository.save(user);
	}

}
