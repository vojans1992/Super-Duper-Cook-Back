package com.tim1.cook.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.tim1.cook.entities.RecipeEntity;
import com.tim1.cook.repositories.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	RecipeRepository repository;

	@Override
	public RecipeEntity saveRecipe(RecipeEntity newRecipe) {
		RecipeEntity entity;
		if(newRecipe.getId() != null) {
			try {
				entity = repository.findById(newRecipe.getId()).get();
				return entity;
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("Recipe with ID: " + newRecipe.getId() + " does not exist.");
			}
		}else {
			entity = new RecipeEntity();
		}
		entity.setAuthor(newRecipe.getAuthor());
		entity.setDescription(newRecipe.getDescription());
		entity.setGuide(newRecipe.getGuide());
		entity.setPreparationTime(newRecipe.getPreparationTime());
		entity.setQuantity(newRecipe.getQuantity());
		entity.setRecipeIngredientRatios(newRecipe.getRecipeIngredientRatios());
		entity.setTitle(newRecipe.getTitle());
		return repository.save(entity);
	}

	@Override
	public String deleteById(Integer id) {
		
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

}
