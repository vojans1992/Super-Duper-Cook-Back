package com.tim1.cook.service;

import com.tim1.cook.entities.RecipeEntity;

public interface RecipeService {

	public RecipeEntity saveRecipe(RecipeEntity newRecipe);
	public String deleteById(Integer id);
	public RecipeEntity findById(int id);
}
