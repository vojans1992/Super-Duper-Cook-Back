package com.tim1.cook.service;

import java.util.List;

import com.tim1.cook.entities.IngredientEntity;

public interface IngredientService {
    IngredientEntity saveIngredient(IngredientEntity ingredient);
    IngredientEntity getIngredientById(Integer id);
    void deleteIngredientById(Integer id);
    List<IngredientEntity> getAllIngredients();

    
}
