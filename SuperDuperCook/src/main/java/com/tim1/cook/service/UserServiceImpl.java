package com.tim1.cook.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tim1.cook.entities.RoleEntity;
import com.tim1.cook.entities.UserEntity;
import com.tim1.cook.entities.dto.UserDTO;
import com.tim1.cook.repositories.RoleRepository;
import com.tim1.cook.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Iterable<UserEntity> getAllUsers() {
		Iterable<UserEntity> users = userRepository.findAll();
		for (UserEntity user : users) {
			if (!isUsernameUnique(user.getUsername(), user.getId())) {
				throw new NonUniqueUsernameException("Non-unique username found: " + user.getUsername());
			}
		}
		return users;
	}

	public boolean isUsernameUnique(String username, Integer id) {
		UserEntity existingUser = userRepository.findByUsername(username);
		return existingUser == null || existingUser.getId().equals(id);
	}

	public class NonUniqueUsernameException extends RuntimeException {
		public NonUniqueUsernameException(String message) {
			super(message);
		}
	}

	@Override
	public UserEntity createUser(UserEntity newUser) {
		String username = newUser.getUsername();
		String password = newUser.getPassword();
		RoleEntity role = roleRepository.findById(3).get();

		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Username and password are required fields.");
		}

		if (userRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("Username already exists.");
		}

		UserEntity user = new UserEntity();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);

		return userRepository.save(user);
	}

	@Override
	public UserEntity updateUser(Integer id, UserEntity updatedUser) {
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found."));

		if (userRepository.existsByUsernameAndIdNot(updatedUser.getUsername(), id)) {
			throw new IllegalArgumentException("Username already exists.");
		}

		user.setUsername(updatedUser.getUsername());
		user.setPassword(updatedUser.getPassword());

		return userRepository.save(user);
	}

	public class UserNotFoundException extends RuntimeException {
		public UserNotFoundException(String message) {
			super(message);
		}
	}

	@Override
	public void deleteUser(Integer id) {
		Optional<UserEntity> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty()) {
			throw new UserNotFoundException("User with ID " + id + " not found.");
		}

		UserEntity admin = userOptional.get();
		userRepository.delete(admin);
	}

	@Override
	public UserEntity addRoleToUser(Integer userId, Integer roleId) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User with ID " + userId + " not found."));
		RoleEntity role = roleRepository.findById(roleId)
				.orElseThrow(() -> new NoSuchElementException("Role with ID " + roleId + " not found."));

		user.setRole(role);

		return userRepository.save(user);
	}

	@Override
	public UserDTO updateUserDTO(Integer id, UserDTO dto) {
		Optional<UserEntity> u = userRepository.findById(id);
		if (u.isPresent()) {
			UserEntity us = u.get();
			us.setUsername(dto.getUsername());
			us.setPassword(dto.getPassword());
			us.setRole(dto.getRole());
			userRepository.save(us);
		}
		return new UserDTO();
	}

}
