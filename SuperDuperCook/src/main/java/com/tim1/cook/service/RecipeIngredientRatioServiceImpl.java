package com.tim1.cook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim1.cook.entities.IngredientEntity;
import com.tim1.cook.entities.RecipeEntity;
import com.tim1.cook.entities.RecipeIngredientRatioEntity;
import com.tim1.cook.entities.RecipeIngredientRatioKey;
import com.tim1.cook.repositories.RecipeIngredientRatioRepository;

@Service
public class RecipeIngredientRatioServiceImpl implements RecipeIngredientRatioService{
	
	@Autowired RecipeIngredientRatioRepository repository;
	@Autowired IngredientService ingredientService;
	
	@Override
	public RecipeIngredientRatioEntity findById(int recipeId, int ingredientId) {
		RecipeIngredientRatioEntity entity;
		RecipeIngredientRatioKey key = new RecipeIngredientRatioKey(recipeId, ingredientId);
		try {
			entity = repository.findById(key).get();
			return entity;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Entity with id: " + key + " does not exist.");
		}
		
	}

	@Override
	public RecipeIngredientRatioEntity save(RecipeEntity recipe, int ingredientId, int amount) {
		
		IngredientEntity ingredient;
		try {
			ingredient = ingredientService.getIngredientById(ingredientId);
		} catch (ClassCastException e) {
			throw new ClassCastException("Ingredient with ID: " + ingredientId + " is not an ingredient entity.");
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		
		RecipeIngredientRatioEntity entity = new RecipeIngredientRatioEntity();
		entity.setRecipe(recipe);
		entity.setIngredient(ingredient);
		entity.setAmount(amount);
		
		repository.save(entity);
		return entity;
	}

	@Override
	public RecipeIngredientRatioEntity delete(int recipeId, int ingredientId) {
		RecipeIngredientRatioEntity entity = findById(recipeId, ingredientId);
		repository.delete(entity);
		return entity;
	}

	@Override
	public List<RecipeIngredientRatioEntity> findByRecipeId(Integer id) {
		ArrayList<RecipeIngredientRatioEntity> list = new ArrayList<RecipeIngredientRatioEntity>();
		list = repository.findAllByRecipeId(id);
		return list;
	}

	@Override
	public void deleteAllByRecipeId(Integer id) {
		repository.deleteAllByRecipeId(id);
		
	}
}
