package com.tim1.cook.service;

import com.tim1.cook.entities.RecipeIngredientRatioEntity;

public interface RecipeIngredientRatioService {

	RecipeIngredientRatioEntity save(int recipeId, int ingredientId, int amount);

	RecipeIngredientRatioEntity findById(int recipeId, int ingredientId);

	RecipeIngredientRatioEntity delete(int recipeId, int ingredientId);
}
