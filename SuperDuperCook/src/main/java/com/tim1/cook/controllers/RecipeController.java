package com.tim1.cook.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
import com.tim1.cook.entities.RecipeEntity;
import com.tim1.cook.entities.dto.RecipeDTO;
import com.tim1.cook.repositories.RecipeRepository;
import com.tim1.cook.service.RecipeService;

@RestController
@RequestMapping(value = "/api/v1/recipes")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {
	
	@Autowired RecipeService recipeService;
	@Autowired RecipeRepository recipeRepository;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewRecipe(@Valid @RequestBody RecipeDTO newRecipe) {
		logger.info("/api/v1/recipes/newRecipe started.");
		return new ResponseEntity<>(recipeService.saveRecipe(newRecipe), HttpStatus.OK);
	}
	
	@RequestMapping
	public ResponseEntity<?> getAllRecipes() {
		logger.info("/api/v1/recipes/getAllRecipes started.");
		return new ResponseEntity<List<RecipeEntity>>((List<RecipeEntity>) recipeRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		logger.info("/api/v1/recipes/getOne started.");
		try {
			return new ResponseEntity<>(recipeService.findById(id), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateRecipe(@Valid @RequestBody RecipeDTO newRecipe, @PathVariable int id) {
		logger.info("/api/v1/recipes/updateRecipe started.");
		newRecipe.setId(id);
		return addNewRecipe(newRecipe);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteRecipe(@PathVariable int id) {
		try {
			return new ResponseEntity<String>(
					recipeService.deleteById(id), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)  
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {   
			String fieldName = "";
			String errorMessage = error.getDefaultMessage();
			if (error instanceof FieldError) {        
				fieldName = ((FieldError)error).getField();
				errorMessage = error.getDefaultMessage();
			} else if (error instanceof ObjectError) {
				fieldName = ((ObjectError)error).getObjectName();
				errorMessage = error.getDefaultMessage();
				}					
			errors.put(fieldName, errorMessage);  
		});
		return errors;
	}
}
