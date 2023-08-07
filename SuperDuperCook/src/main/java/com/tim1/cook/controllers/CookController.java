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
import com.fasterxml.jackson.annotation.JsonView;
import com.tim1.cook.controllers.util.RESTError;
import com.tim1.cook.entities.CookEntity;
import com.tim1.cook.entities.UserEntity;
import com.tim1.cook.repositories.CookRepository;
import com.tim1.cook.repositories.RoleRepository;
import com.tim1.cook.repositories.UserRepository;
import com.tim1.cook.service.CookService;
import com.tim1.cook.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/cook")
@CrossOrigin(origins = "http://localhost:3000")
public class CookController {

	@Autowired
	private CookRepository cookRepository;
	@Autowired
	private CookService cookService;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createCook(@Valid @RequestBody CookEntity newCook) {
		logger.info("/api/v1/cook/createCook started.");

		if (newCook.getUsername() == null || newCook.getUsername().isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Please provide a username"), HttpStatus.BAD_REQUEST);
		}

		if (newCook.getPassword() == null || newCook.getPassword().isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Please provide a password"), HttpStatus.BAD_REQUEST);
		}

		if (cookRepository.existsByUsername(newCook.getUsername())) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Username already exists"), HttpStatus.BAD_REQUEST);
		}

		try {
			CookEntity cook = cookService.createCook(newCook);
			logger.info("Finished OK.");
			return new ResponseEntity<>(cook, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Exception occurred: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(4, "Failed to create cook"), HttpStatus.BAD_REQUEST);
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserEntity>> getAllCooks() {
		logger.info("/api/v1/cook/getAllCooks started.");
		Iterable<CookEntity> cooks = cookRepository.findAll();
		logger.info("Finished OK.");
		return new ResponseEntity(cooks, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateCook(@PathVariable Integer id, @RequestBody CookEntity updatedCook) {
		logger.info("/api/v1/cook/updateCook started.");

		if (updatedCook.getUsername() == null || updatedCook.getUsername().equals("")) {
			logger.info("Username is null.");
			return new ResponseEntity<RESTError>(new RESTError(1, "Please provide a username"), HttpStatus.BAD_REQUEST);
		}
		if (updatedCook.getPassword() == null || updatedCook.getPassword().equals("")) {
			logger.info("Password is null.");
			return new ResponseEntity<RESTError>(new RESTError(2, "Please provide a password"), HttpStatus.BAD_REQUEST);
		}
		if (cookRepository.existsByUsernameAndIdNot(updatedCook.getUsername(), id)) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Username already exists"), HttpStatus.BAD_REQUEST);
		}

		try {
			CookEntity cook = cookService.updateCook(id, updatedCook);
			logger.info("Cook updated successfully.");
			return new ResponseEntity<CookEntity>(cook, HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			logger.error("Exception occurred: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(3, "Cook with ID: " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteCook(@PathVariable Integer id) {
		logger.info("/api/v1/cook/deleteCook started.");
		Optional<CookEntity> cookOptional = cookRepository.findById(id);
		if (cookOptional.isEmpty()) {
			logger.error("Cook with ID " + id + " not found.");
			return new ResponseEntity<RESTError>(new RESTError(404, "Cook not found"), HttpStatus.NOT_FOUND);
		}
		CookEntity cook = cookOptional.get();
		cookRepository.delete(cook);
		logger.info("Cook with ID " + id + " deleted.");
		return new ResponseEntity<>(cook, HttpStatus.OK);
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
