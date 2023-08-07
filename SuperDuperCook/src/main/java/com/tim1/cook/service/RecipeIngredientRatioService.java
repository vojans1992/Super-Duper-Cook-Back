package com.tim1.cook.service;

import com.tim1.cook.entities.RecipeEntity;
import com.tim1.cook.entities.RecipeIngredientRatioEntity;

public interface RecipeIngredientRatioService {

	RecipeIngredientRatioEntity save(RecipeEntity recipe, int ingredientId, int amount);

	RecipeIngredientRatioEntity findById(int recipeId, int ingredientId);

	RecipeIngredientRatioEntity delete(int recipeId, int ingredientId);

	Iterable<RecipeIngredientRatioEntity> findByRecipeId(Integer id);
}
