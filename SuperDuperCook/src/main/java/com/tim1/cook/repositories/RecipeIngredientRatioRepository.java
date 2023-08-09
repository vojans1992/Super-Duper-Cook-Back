package com.tim1.cook.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.tim1.cook.entities.RecipeIngredientRatioEntity;
import com.tim1.cook.entities.RecipeIngredientRatioKey;

public interface RecipeIngredientRatioRepository extends CrudRepository<RecipeIngredientRatioEntity, RecipeIngredientRatioKey> {

	ArrayList<RecipeIngredientRatioEntity> findAllByRecipeId(Integer recipeId);

	void deleteAllByRecipeId(Integer id);
}
