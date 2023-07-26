package com.tim1.cook.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "recipe_ingredient_ratio")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class RecipeIngredientRatioEntity {

	@EmbeddedId
	@Column(name = "recipe_ingredient_ratio_id")
	private RecipeIngredientRatioKey id = new RecipeIngredientRatioKey();
	
	@Column(name = "amount", columnDefinition = "integer default 0")
	private Integer amount;
	
	@NotNull(message = "Recipe ingredient ratio must be connected to a recipe.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@MapsId("recipeId")
	@JoinColumn (name= "recipe_id", nullable = false)
	private RecipeEntity recipe;
	
	@NotNull(message = "Recipe ingredient ratio must be connected to an ingredient.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@MapsId("ingredientId")
	@JoinColumn (name= "ingredient_id", nullable = false)
	private IngredientEntity ingredient;

	public RecipeIngredientRatioEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecipeIngredientRatioKey getId() {
		return id;
	}

	public void setId(RecipeIngredientRatioKey id) {
		this.id = id;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public RecipeEntity getRecipe() {
		return recipe;
	}

	public void setRecipe(RecipeEntity recipe) {
		this.recipe = recipe;
	}

	public IngredientEntity getIngredient() {
		return ingredient;
	}

	public void setIngredient(IngredientEntity ingredient) {
		this.ingredient = ingredient;
	}
	
	
	
}
