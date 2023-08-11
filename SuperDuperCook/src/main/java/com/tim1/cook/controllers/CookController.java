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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.tim1.cook.controllers.util.RESTError;
import com.tim1.cook.entities.CookEntity;
import com.tim1.cook.service.AdminServiceImpl.NonUniqueUsernameException;
import com.tim1.cook.service.CookService;
import com.tim1.cook.service.CookServiceImpl.CookNotFoundException;

@RestController
@RequestMapping(value = "/api/v1/cook")
@CrossOrigin(origins = "http://localhost:3000")
public class CookController {

	@Autowired
	private CookService cookService;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createCook(@Valid @RequestBody CookEntity newCook) {
		logger.info("/api/v1/cook/createCook started.");
		try {
			CookEntity cook = cookService.createCook(newCook);
			logger.info("Cook created successfully.");
			return new ResponseEntity<>(cook, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException occurred: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(3, e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Exception occurred: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(4, "Failed to create cook"), HttpStatus.BAD_REQUEST);
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllCooks() {
		logger.info("/api/v1/admins/getAllCooks started.");

		try {
			Iterable<CookEntity> cooks = cookService.getAllCooks();
			logger.info("Finished OK.");
			return new ResponseEntity<>(cooks, HttpStatus.OK);
		} catch (NonUniqueUsernameException e) {
			logger.error("Non-unique username found: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(1, e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Exception occurred while retrieving cooks: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(500, "Failed to retrieve admins"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateCook(@PathVariable Integer id, @RequestBody CookEntity updatedCook) {
		logger.info("/api/v1/cook/updateCook started.");

		try {
			CookEntity cook = cookService.updateCook(id, updatedCook);
			logger.info("/api/v1/admins/updateAdmin finished.");
			return new ResponseEntity<>(cook, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logger.error("Cook with ID " + id + " not found.");
			return new ResponseEntity<RESTError>(new RESTError(404, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			logger.error("Failed to update cook: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(400, e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Exception occurred while updating cook: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(500, "Failed to update cook"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteCook(@PathVariable Integer id) {
		logger.info("/api/v1/cook/deleteCook started.");

		try {
			cookService.deleteCook(id);
			logger.info("Cook with ID " + id + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (CookNotFoundException e) {
			logger.error("Cook with ID " + id + " not found.");
			return new ResponseEntity<RESTError>(new RESTError(404, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Exception occurred while deleting cook: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(500, "Failed to delete admin"),
					HttpStatus.INTERNAL_SERVER_ERROR);
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
