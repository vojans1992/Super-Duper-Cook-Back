package com.tim1.cook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tim1.cook.entities.CookEntity;
import com.tim1.cook.entities.IngredientEntity;
import com.tim1.cook.entities.RecipeEntity;
import com.tim1.cook.entities.RecipeIngredientRatioEntity;
import com.tim1.cook.entities.RecipeIngredientRatioKey;
import com.tim1.cook.entities.UserEntity;
import com.tim1.cook.entities.dto.IngredientIdRatioDTO;
import com.tim1.cook.entities.dto.RecipeDTO;
import com.tim1.cook.repositories.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	RecipeRepository repository;
	@Autowired
	RecipeIngredientRatioService recipeIngredientRatioService;
	@Autowired
	CookService cookService;
	@Autowired
	IngredientService ingredientService;
	@Autowired
	UserService userService;

	@Override
	public RecipeEntity saveRecipe(RecipeDTO newRecipe) {
		RecipeEntity entity;
		if (newRecipe.getId() != 0) {
			try {
				entity = repository.findById(newRecipe.getId()).get();
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("Recipe with ID: " + newRecipe.getId() + " does not exist.");
			}
		} else {
			entity = new RecipeEntity();
		}
		entity.setDescription(newRecipe.getDescription());
		entity.setGuide(newRecipe.getGuide());
		entity.setPreparationTime(newRecipe.getPreparationTime());
		entity.setQuantity(newRecipe.getQuantity());
		entity.setTitle(newRecipe.getTitle());

		CookEntity author;
		try {
			author = cookService.findCookByUsername(newRecipe.getAuthorUsername());
			entity.setAuthor(author);
		} catch (ClassCastException e) {
			throw new ClassCastException("Cook with ID: " + newRecipe.getAuthorUsername() + " is not a cook entity.");
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		entity.setRecipeIngredientRatios(createIngredientRatios(newRecipe.getIngredientIds(), repository.save(entity)));

		calculateStats(entity);
		
		return repository.save(entity);
	}
	
	public void calculateStats(RecipeEntity recipe) {
		ArrayList<RecipeIngredientRatioEntity> list = (ArrayList<RecipeIngredientRatioEntity>) recipeIngredientRatioService.findByRecipeId(recipe.getId());
		for (RecipeIngredientRatioEntity rire : list) {
			recipe.setCalories((rire.getIngredient().getCalories() * rire.getAmount() / Integer.parseInt(rire.getIngredient().getMeasurementUnit().split(" ")[0])) + recipe.getCalories());
			recipe.setCarboHydrate((rire.getIngredient().getCarboHydrate() * rire.getAmount() / Integer.parseInt(rire.getIngredient().getMeasurementUnit().split(" ")[0])) + recipe.getCarboHydrate());
			recipe.setFat((rire.getIngredient().getFat() * rire.getAmount() / Integer.parseInt(rire.getIngredient().getMeasurementUnit().split(" ")[0])) + recipe.getFat());
			recipe.setProtein((rire.getIngredient().getProtein() * rire.getAmount() / Integer.parseInt(rire.getIngredient().getMeasurementUnit().split(" ")[0])) + recipe.getProtein());
			recipe.setSaturatedFat((rire.getIngredient().getSaturatedFat() * rire.getAmount() / Integer.parseInt(rire.getIngredient().getMeasurementUnit().split(" ")[0])) + recipe.getSaturatedFat());
			recipe.setSugar((rire.getIngredient().getSugar() * rire.getAmount() / Integer.parseInt(rire.getIngredient().getMeasurementUnit().split(" ")[0])) + recipe.getSugar());
		}
	}
	@Transactional
	@Override
	public ArrayList<RecipeIngredientRatioEntity> createIngredientRatios(IngredientIdRatioDTO[] ratiosDTO, RecipeEntity recipe) {
		ArrayList<RecipeIngredientRatioEntity> ratios = new ArrayList<>();
		IngredientEntity ingredient;
		int amount;
		IngredientIdRatioDTO ingredientIdRatio;
		RecipeIngredientRatioEntity ratio;
		RecipeIngredientRatioKey key;
		for (int i = 0; i < ratiosDTO.length; i++) {
			ingredientIdRatio = ratiosDTO[i];
			try {
				ingredient = ingredientService.getIngredientById(ingredientIdRatio.getIngredientId());
				amount = ingredientIdRatio.getAmount();
				key = new RecipeIngredientRatioKey(recipe.getId(), ingredient.getId());
				ratio = new RecipeIngredientRatioEntity();
				ratio.setId(key);
				ratio.setAmount(amount);
				ratios.add(ratio);
				recipeIngredientRatioService.save(recipe, ingredient.getId(), amount);
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		}
		return ratios;
	}

	@Transactional
	@Override
	public String deleteById(Integer id) {
		recipeIngredientRatioService.deleteAllByRecipeId(id);

		try {
			repository.deleteById(id);
			return "Deleted recipe with ID: " + id;
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("Recipe with ID: " + id + " does not exist.", 1);
		}
	}

	@Override
	public RecipeEntity findById(int id) {
		RecipeEntity entity;
		try {
			entity = repository.findById(id).get();
			return entity;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Recipe with ID: " + id + " does not exist.");
		}
	}

	@Override
	public RecipeEntity addToFav(int recipeId, String username) {
		UserEntity user = userService.findByUsername(username);
		RecipeEntity recipe = repository.findById(recipeId).get();
		if(user.getRecipes().contains(recipe)) {
			return recipe;
		}
		user.getRecipes().add(recipe);
		userService.updateUser(user.getId(), user);
		
		return recipe;
	}

	@Override
	public RecipeEntity removeAsFav(int recipeId, String username) {
		UserEntity user = userService.findByUsername(username);
		RecipeEntity recipe = repository.findById(recipeId).get();
		
		user.getRecipes().remove(recipe);
		userService.updateUser(user.getId(), user);
		
		return recipe;
	}

}
