package com.tim1.cook.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim1.cook.controllers.util.RESTError;
import com.tim1.cook.entities.RecipeEntity;
import com.tim1.cook.entities.RecipeIngredientRatioEntity;
import com.tim1.cook.repositories.RecipeIngredientRatioRepository;
import com.tim1.cook.service.RecipeIngredientRatioService;

@RestController
@RequestMapping(value = "/api/v1/ratios")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeIngredientRatioController {
	
	@Autowired RecipeIngredientRatioService service;
	@Autowired RecipeIngredientRatioRepository repository;
	
	private final Logger logger= (Logger) LoggerFactory.getLogger(this.getClass());
	
//	@RequestMapping(method = RequestMethod.POST)
//	public ResponseEntity<?> addNewRatio(@Valid @RequestBody RecipeIngredientRatioEntity newRatio) {
//		logger.info("/api/v1/ratios/addNewRatio started.");
//		return new ResponseEntity<>(service.save(newRatio.getRecipe().getId(), newRatio.getIngredient().getId(), newRatio.getAmount()), HttpStatus.OK);
//	}
	
	@RequestMapping
	public ResponseEntity<?> getAllRatios() {
		logger.info("/api/v1/ratios/getAllRatios started.");
		return new ResponseEntity<List<RecipeIngredientRatioEntity>>((List<RecipeIngredientRatioEntity>) repository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable int recipeId, @PathVariable int ingredientId) {
		logger.info("/api/v1/ratios/getOne started.");
		try {
			return new ResponseEntity<>(service.findById(recipeId, ingredientId), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteRatio(@PathVariable int recipeId, @PathVariable int ingredientId) {
		try {
			service.delete(recipeId, ingredientId);
			String string = "Deleted ratio " + recipeId + ingredientId;
			return new ResponseEntity<String>(string, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
}
