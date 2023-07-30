package com.tim1.cook.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tim1.cook.entities.AllergenEntity;
import com.tim1.cook.entities.IngredientEntity;
import com.tim1.cook.entities.dto.IngredientDTO;
import com.tim1.cook.repositories.AllergenRepository;
import com.tim1.cook.repositories.IngredientRepository;
import com.tim1.cook.service.IngredientService;
import com.tim1.cook.service.UserService;

@RestController
@RequestMapping("/api/v1/ingredients")
@CrossOrigin(origins = "http://localhost:3000")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @Autowired
	private AllergenRepository allergenRepository;
    @Autowired
   	private IngredientRepository ingredientRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping   
    public ResponseEntity<IngredientEntity> createIngredient(@RequestBody IngredientEntity ingredient) {
        IngredientEntity savedIngredient = ingredientService.saveIngredient(ingredient);
        return ResponseEntity.ok(savedIngredient);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<IngredientEntity> getIngredient(@PathVariable Integer id) {
        IngredientEntity ingredient = ingredientService.getIngredientById(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<List<IngredientEntity>>getAllIngredients(){
    	List<IngredientEntity> ingredients = ingredientService.getAllIngredients();
    	return ResponseEntity.ok(ingredients);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Integer id) {
        IngredientEntity ingredient = ingredientService.getIngredientById(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        ingredientService.deleteIngredientById(id);
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")    
    public ResponseEntity<IngredientEntity> updateIngredient(@PathVariable Integer id, @RequestBody IngredientEntity updatedIngredient) {
        IngredientEntity existingIngredient = ingredientService.getIngredientById(id);
        if (existingIngredient == null) {
            return ResponseEntity.notFound().build();
        }

        existingIngredient.setName(updatedIngredient.getName());
        existingIngredient.setMeasurementUnit(updatedIngredient.getMeasurementUnit());
        existingIngredient.setCalories(updatedIngredient.getCalories());
        existingIngredient.setCarboHydrate(updatedIngredient.getCarboHydrate());
        existingIngredient.setSugar(updatedIngredient.getSugar());
        existingIngredient.setFat(updatedIngredient.getFat());
        existingIngredient.setSaturatedFat(updatedIngredient.getSaturatedFat());
        existingIngredient.setProtein(updatedIngredient.getProtein());
        existingIngredient.setAllergens(updatedIngredient.getAllergens());

        IngredientEntity updatedIngredientEntity = ingredientService.saveIngredient(existingIngredient);
        return ResponseEntity.ok(updatedIngredientEntity);
    }
   
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method=RequestMethod.POST, value = "/new")
    public IngredientDTO newIngredient(@RequestBody IngredientDTO dto) {
    	IngredientEntity in = new IngredientEntity();
    	in.setName(dto.getName());
    	in.setMeasurementUnit(dto.getMeasurementUnit());
    	in.setCalories(dto.getCalories());
    	in.setCarboHydrate(dto.getCarboHydrate());
    	in.setSugar(dto.getSugar());
    	in.setFat(dto.getFat());
    	in.setSaturatedFat(dto.getSaturatedFat());
    	in.setProtein(dto.getProtein());
    	for(String a : dto.getAllergens()) {
    		List<AllergenEntity> la = allergenRepository.findByName(a);
    		if(la.isEmpty()) {
    			AllergenEntity na = new AllergenEntity();
    			na.setName(a);
    			na.setIcon(a);
    			allergenRepository.save(na);
    			in.getAllergens().add(na);
    		}else {
    			in.getAllergens().add(la.get(0));
    		}
    	}
		ingredientRepository.save(in);
    	return new IngredientDTO();
    	
    }
    
    @RequestMapping(method=RequestMethod.PUT, value="/dto/{id}")
	public ResponseEntity<IngredientDTO> updateIngredientDTO(@PathVariable Integer id, @RequestBody IngredientDTO dto) {
		Optional<IngredientEntity> o = ingredientRepository.findById(id);
		if(o.isPresent()){
			IngredientEntity in = o.get();
			in.setName(dto.getName());
	    	in.setMeasurementUnit(dto.getMeasurementUnit());
	    	in.setCalories(dto.getCalories());
	    	in.setCarboHydrate(dto.getCarboHydrate());
	    	in.setSugar(dto.getSugar());
	    	in.setFat(dto.getFat());
	    	in.setSaturatedFat(dto.getSaturatedFat());
	    	in.setProtein(dto.getProtein());
			
			in.getAllergens().clear();
			for(String a : dto.getAllergens()) {
				List<AllergenEntity> la = allergenRepository.findByName(a);
				if(la.isEmpty()) {
					AllergenEntity na = new AllergenEntity();
					na.setName(a);
					allergenRepository.save(na);
					in.getAllergens().add(na);
				}else {
					in.getAllergens().add(la.get(0));
				}
			}
			ingredientRepository.save(in);		
			return new ResponseEntity<IngredientDTO>(new IngredientDTO(), HttpStatus.OK);
		}else{
			return new ResponseEntity<IngredientDTO>(HttpStatus.NOT_FOUND);
		}
	}

}