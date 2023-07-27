package com.tim1.cook.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RecipeIngredientRatioKey implements Serializable {

	@Column(name = "recipe_id")
	private Integer recipeId;
	
	@Column(name = "ingredient_id")
	private Integer ingredientId;

	public RecipeIngredientRatioKey() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public RecipeIngredientRatioKey(Integer recipeId, Integer ingredientId) {
		super();
		this.recipeId = recipeId;
		this.ingredientId = ingredientId;
	}



	public Integer getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Integer recipeId) {
		this.recipeId = recipeId;
	}

	public Integer getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Integer ingredientId) {
		this.ingredientId = ingredientId;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
