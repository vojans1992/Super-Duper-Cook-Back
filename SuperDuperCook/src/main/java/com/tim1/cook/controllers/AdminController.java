package com.tim1.cook.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.tim1.cook.controllers.util.RESTError;
import com.tim1.cook.entities.AdminEntity;
import com.tim1.cook.repositories.AdminRepository;
import com.tim1.cook.service.AdminService;

@RestController
@RequestMapping(value = "/api/v1/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private AdminService adminService;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	// @Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createAdmin(@Valid @RequestBody AdminEntity newAdmin) {
		logger.info("/api/v1/admin/createAdmin started.");

		if (newAdmin.getUsername() == null || newAdmin.getUsername().isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Please provide a username"), HttpStatus.BAD_REQUEST);
		}

		if (newAdmin.getPassword() == null || newAdmin.getPassword().isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Please provide a password"), HttpStatus.BAD_REQUEST);
		}

		if (adminRepository.existsByUsername(newAdmin.getUsername())) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Username already exists"), HttpStatus.BAD_REQUEST);
		}

		try {
			AdminEntity admin = adminService.createAdmin(newAdmin);
			logger.info("Finished OK.");
			return new ResponseEntity<>(admin, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Exception occurred: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(4, "Failed to create admin"), HttpStatus.BAD_REQUEST);
		}
	}

	// @Secured("ROLE_ADMIN")
	// @JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllAdmins() {
		logger.info("/api/v1/admin/getAllAdmins started.");

		Iterable<AdminEntity> admins = adminRepository.findAll();
		for (AdminEntity admin : admins) {
			if (!isUsernameUnique(admin.getUsername(), admin.getId())) {
				logger.error("Non-unique username found: " + admin.getUsername());
				return new ResponseEntity<RESTError>(
						new RESTError(1, "Non-unique username found: " + admin.getUsername()), HttpStatus.BAD_REQUEST);
			}
		}

		logger.info("Finished OK.");
		return new ResponseEntity<AdminEntity>(HttpStatus.OK);
	}

	private boolean isUsernameUnique(String username, Integer id) {
		AdminEntity existingAdmin = adminRepository.findByUsername(username);
		return existingAdmin == null || existingAdmin.getId().equals(id);
	}

	// @Secured("ROLE_ADMIN")
	// @JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @RequestBody AdminEntity updatedAdmin) {
		logger.info("/api/v1/admin/updateAdmin started.");

		if (updatedAdmin.getUsername() == null || updatedAdmin.getUsername().equals("")) {
			logger.info("Username is null.");
			return new ResponseEntity<RESTError>(new RESTError(1, "Please provide a username"), HttpStatus.BAD_REQUEST);
		}
		if (updatedAdmin.getPassword() == null || updatedAdmin.getPassword().equals("")) {
			logger.info("Password is null.");
			return new ResponseEntity<RESTError>(new RESTError(2, "Please provide a password"), HttpStatus.BAD_REQUEST);
		}
		if (adminRepository.existsByUsernameAndIdNot(updatedAdmin.getUsername(), id)) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Username already exists"), HttpStatus.BAD_REQUEST);
		}
		AdminEntity admin = adminService.updateAdmin(id, updatedAdmin);
		logger.info("/api/v1/admin/updateAdmin finished.");
		return new ResponseEntity<AdminEntity>(admin, HttpStatus.CREATED);
	}

	// @Secured("ROLE_ADMIN")
	// @JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteAdmin(@PathVariable Integer id) {
	    logger.info("/api/v1/admins/deleteAdmin started.");

	    Optional<AdminEntity> adminOptional = adminRepository.findById(id);
	    if (adminOptional.isEmpty()) {
	        logger.error("Admin with ID " + id + " not found.");
	        return new ResponseEntity<RESTError>(new RESTError(404, "Admin not found"), HttpStatus.NOT_FOUND);
	    }

	    AdminEntity admin = adminOptional.get();
	    adminRepository.delete(admin);
	    logger.info("Admin with ID " + id + " deleted.");
	    return new ResponseEntity<>(admin, HttpStatus.OK);
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
