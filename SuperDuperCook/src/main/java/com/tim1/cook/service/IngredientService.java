package com.tim1.cook.service;

import java.util.List;

import com.tim1.cook.entities.IngredientEntity;
import com.tim1.cook.entities.dto.IngredientDTO;

public interface IngredientService {
	IngredientEntity saveIngredient(IngredientEntity ingredient);

	IngredientEntity getIngredientById(Integer id);

	void deleteIngredientById(Integer id);

	List<IngredientEntity> getAllIngredients();

	public IngredientEntity updateIngredient(Integer id, IngredientEntity updatedIngredient);

	public IngredientDTO createNewIngredient(IngredientDTO dto);

	public IngredientDTO updateIngredientDTO(Integer id, IngredientDTO dto);
}
