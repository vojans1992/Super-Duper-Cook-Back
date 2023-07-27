package com.tim1.cook.repositories;

import org.springframework.data.repository.CrudRepository;

import com.tim1.cook.entities.RecipeEntity;

public interface RecipeRepository extends CrudRepository<RecipeEntity, Integer> {

}
