package com.tim1.cook.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import com.tim1.cook.controllers.util.RESTError;
import com.tim1.cook.entities.UserEntity;
import com.tim1.cook.repositories.RoleRepository;
import com.tim1.cook.repositories.UserRepository;
import com.tim1.cook.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleRepository roleRepository;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	// @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@Valid @RequestBody UserEntity newUser) {
		logger.info("/api/v1/users/createUser started.");
		if (newUser.getUsername() == null || newUser.getUsername().isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Please provide a username"), HttpStatus.BAD_REQUEST);
		}

		if (newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Please provide a password"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByUsername(newUser.getUsername())) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Username already exists"), HttpStatus.BAD_REQUEST);
		}

		try {
			UserEntity user = userService.createUser(newUser);
			logger.info("Finished OK.");
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Exception occurred: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(4, "Failed to create user"), HttpStatus.BAD_REQUEST);
		}
	}

	// @Secured("ROLE_ADMIN")
	// @JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserEntity>> getAllUsers() {
		logger.info("/api/v1/users/getAllUsers started.");
		List<UserEntity> users = userRepository.findAll();
		logger.info("Finished OK.");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// @Secured("ROLE_ADMIN")
	// @JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserEntity updatedUser) {
		logger.info("/api/v1/users/updateUser started.");

		if (updatedUser.getUsername() == null || updatedUser.getUsername().equals("")) {
			logger.info("Username is null.");
			return new ResponseEntity<RESTError>(new RESTError(1, "Please provide a username"), HttpStatus.BAD_REQUEST);
		}
		if (updatedUser.getPassword() == null || updatedUser.getPassword().equals("")) {
			logger.info("Password is null.");
			return new ResponseEntity<RESTError>(new RESTError(2, "Please provide a password"), HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByUsernameAndIdNot(updatedUser.getUsername(), id)) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Username already exists"), HttpStatus.BAD_REQUEST);
		}
		UserEntity user = userService.updateUser(id, updatedUser);
		logger.info("/api/v1/user/updateUser finished.");
		return new ResponseEntity<UserEntity>(user, HttpStatus.CREATED);
	}

	// @Secured("ROLE_ADMIN")
	// @JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		logger.info("/api/v1/user/deleteUser started.");
		Optional<UserEntity> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty()) {
			logger.error("User with ID " + id + " not found.");
			return new ResponseEntity<RESTError>(new RESTError(404, "User not found"), HttpStatus.NOT_FOUND);
		}
		UserEntity user = userOptional.get();
		userRepository.delete(user);
		logger.info("User with ID " + id + " deleted.");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// @Secured("ROLE_ADMIN")
	// @JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/addrole")
	public ResponseEntity<?> addRoleToUser(@PathVariable Integer id, @RequestParam Integer roleId) {
		logger.info("/api/v1/users/addRoleToUser started.");

		try {
			UserEntity user = userService.addRoleToUser(id, roleId);
			logger.info("User found and role added successfully.");
			userRepository.save(user);
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logger.error("Exception occurred: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(1, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			logger.error("Exception occurred: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(2, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = "";
			String errorMessage = error.getDefaultMessage();
			if (error instanceof FieldError) {
				fieldName = ((FieldError) error).getField();
				errorMessage = error.getDefaultMessage();
			} else if (error instanceof ObjectError) {
				fieldName = ((ObjectError) error).getObjectName();
				errorMessage = error.getDefaultMessage();
			}
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
