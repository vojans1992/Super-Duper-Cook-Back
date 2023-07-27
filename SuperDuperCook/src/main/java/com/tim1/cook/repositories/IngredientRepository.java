package com.tim1.cook.repositories;

import org.springframework.data.repository.CrudRepository;

import com.tim1.cook.entities.IngredientEntity;

public interface IngredientRepository extends CrudRepository<IngredientEntity, Integer> {

}
