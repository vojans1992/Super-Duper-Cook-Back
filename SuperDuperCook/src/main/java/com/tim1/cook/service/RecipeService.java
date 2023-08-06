package com.tim1.cook.service;

import java.util.ArrayList;

import com.tim1.cook.entities.RecipeEntity;
import com.tim1.cook.entities.RecipeIngredientRatioEntity;
import com.tim1.cook.entities.dto.IngredientIdRatioDTO;
import com.tim1.cook.entities.dto.RecipeDTO;

public interface RecipeService {

	public RecipeEntity saveRecipe(RecipeDTO newRecipe);
	public String deleteById(Integer id);
	public RecipeEntity findById(int id);
	ArrayList<RecipeIngredientRatioEntity> createIngredientRatios(IngredientIdRatioDTO[] ratiosDTO, RecipeEntity recipe);
}
