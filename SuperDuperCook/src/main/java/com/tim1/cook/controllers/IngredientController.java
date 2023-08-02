package com.tim1.cook.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tim1.cook.entities.IngredientEntity;
import com.tim1.cook.entities.dto.IngredientDTO;
import com.tim1.cook.service.IngredientService;


@RestController
@RequestMapping("/api/v1/ingredients")
@CrossOrigin(origins = "http://localhost:3000")
public class IngredientController {

	private final IngredientService ingredientService;

	@Autowired
	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}	

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
	public ResponseEntity<List<IngredientEntity>> getAllIngredients() {
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

	@PutMapping("/{id}")
	public ResponseEntity<IngredientEntity> updateIngredient(@PathVariable Integer id,@RequestBody IngredientEntity updatedIngredient) {
		IngredientEntity updatedIngredientEntity = ingredientService.updateIngredient(id, updatedIngredient);
		if (updatedIngredientEntity == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(updatedIngredientEntity);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/new")
	public IngredientDTO newIngredient(@RequestBody IngredientDTO dto) {
		return ingredientService.createNewIngredient(dto);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/dto/{id}")
	public ResponseEntity<IngredientDTO> updateIngredientDTO(@PathVariable Integer id, @RequestBody IngredientDTO dto) {
		IngredientDTO updatedDto = ingredientService.updateIngredientDTO(id, dto);
		if (updatedDto != null) {
			return new ResponseEntity<>(updatedDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}