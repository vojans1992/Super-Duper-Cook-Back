package com.tim1.cook.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.tim1.cook.controllers.util.RESTError;
import com.tim1.cook.entities.UserEntity;
import com.tim1.cook.entities.dto.UserDTO;
import com.tim1.cook.repositories.UserRepository;
import com.tim1.cook.service.UserService;
import com.tim1.cook.service.UserServiceImpl.NonUniqueUsernameException;
import com.tim1.cook.service.UserServiceImpl.UserNotFoundException;

@RestController
@RequestMapping(value = "/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@Valid @RequestBody UserEntity newUser) {
		logger.info("/api/v1/users/createUser started.");

		try {
			UserEntity user = userService.createUser(newUser);
			logger.info("User created successfully.");
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException occurred: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(3, e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Exception occurred: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(4, "Failed to create user"), HttpStatus.BAD_REQUEST);
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers() {
		logger.info("/api/v1/users/getAllUsers started.");
		try {
			Iterable<UserEntity> users = userService.getAllUsers();
			logger.info("Finished OK.");
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (NonUniqueUsernameException e) {
			logger.error("Non-unique username found: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(1, e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Exception occurred while retrieving admins: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(500, "Failed to retrieve user"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserEntity updatedUser) {
		logger.info("/api/v1/users/updateUser started.");

		try {
			UserEntity user = userService.updateUser(id, updatedUser);
			logger.info("/api/v1/admins/updateUser finished.");
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logger.error("User with ID " + id + " not found.");
			return new ResponseEntity<RESTError>(new RESTError(404, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			logger.error("Failed to update user: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(400, e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Exception occurred while updating user: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(500, "Failed to update user"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		logger.info("/api/v1/user/deleteUser started.");

		try {
			userService.deleteUser(id);
			logger.info("User with ID " + id + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (UserNotFoundException e) {
			logger.error("User with ID " + id + " not found.");
			return new ResponseEntity<RESTError>(new RESTError(404, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Exception occurred while deleting user: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(500, "Failed to delete user"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/addrole")
	public UserEntity addRoleToUser(@PathVariable Integer id, @RequestParam Integer roleId) {
		logger.info("/dnevnik/users/addRoleToUser started.");
		UserEntity user = userService.addRoleToUser(id, roleId);
		logger.info("User found.");
		userRepository.save(user);
		logger.info("Finished OK.");
		return user;
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/dto/{id}")
	public ResponseEntity<UserDTO> updateUserDTO(@PathVariable Integer id, @RequestBody UserDTO dto) {
		logger.info("/api/v1/admin/updateserDTO started.");
		UserDTO updatedDto = userService.updateUserDTO(id, dto);
		if (updatedDto != null) {
			logger.info("User is not null.");
			logger.info("/api/v1/admin/updateUserDTO finished successfully.");
			return new ResponseEntity<>(updatedDto, HttpStatus.OK);
		} else {
			logger.info("User is null.");
			logger.info("/api/v1/admin/updateUserDTO finished with HttpStatus.NOT_FOUND.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
