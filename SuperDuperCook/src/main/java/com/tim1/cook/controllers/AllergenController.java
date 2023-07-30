package com.tim1.cook.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim1.cook.entities.AllergenEntity;
import com.tim1.cook.service.AllergenService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/allergen")
public class AllergenController {
	
	@Autowired
	AllergenService allergenService;
	
	@RequestMapping(method = RequestMethod.POST)
	 public ResponseEntity<AllergenEntity> createAllergen(@RequestBody AllergenEntity allergen) {
	        AllergenEntity savedAllegen = allergenService.createNewAllergen(allergen);
	        return ResponseEntity.ok(savedAllegen);
	    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<AllergenEntity> getAllergen(@PathVariable Integer id) {
        AllergenEntity allergens = allergenService.getAllergenById(id);
        if (allergens == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allergens);
    }
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<AllergenEntity>>getAllAllergen(){
    	List<AllergenEntity> allergens = allergenService.getAllAllergens();
    	return ResponseEntity.ok(allergens);
    }
	 public ResponseEntity<Void> deleteAllergen(@PathVariable Integer id) {
	        AllergenEntity allergen = allergenService.getAllergenById(id);
	        if (allergen == null) {
	            return ResponseEntity.notFound().build();
	        }
	        allergenService.deleteAllergenById(id);
	        return ResponseEntity.noContent().build();
	    }
}	
