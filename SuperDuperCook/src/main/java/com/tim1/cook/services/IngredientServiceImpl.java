package com.tim1.cook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tim1.cook.entities.IngredientEntity;
import com.tim1.cook.repositories.IngredientRepository;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientEntity saveIngredient(IngredientEntity ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public IngredientEntity getIngredientById(Integer id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteIngredientById(Integer id) {
        ingredientRepository.deleteById(id);
    }

    @Override
    public List<IngredientEntity> getAllIngredients() {
        return (List<IngredientEntity>) ingredientRepository.findAll();
    }

}