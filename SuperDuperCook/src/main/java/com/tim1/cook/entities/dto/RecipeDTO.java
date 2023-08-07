package com.tim1.cook.entities.dto;

import javax.validation.constraints.NotNull;

public class RecipeDTO {

	private int id;
	
	@NotNull(message = "Title must be provided.")
	private String title;
	
	@NotNull(message = "Description must be provided.")
	private String description;

	@NotNull(message = "Guide must be provided.")
	private String guide;
	
	@NotNull(message = "Preparation time must be provided.")
	private int preparationTime;
	
	@NotNull(message = "Quantity must be provided.")
	private String quantity;
	
	@NotNull(message = "Author must be provided.")
	private int authorId;
	
	@NotNull(message = "Ingredients must be provided.")
	private IngredientIdRatioDTO[] ingredientIds;

	public RecipeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public int getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public IngredientIdRatioDTO[] getIngredientIds() {
		return ingredientIds;
	}

	public void setIngredientIds(IngredientIdRatioDTO[] ingredientIds) {
		this.ingredientIds = ingredientIds;
	}

	
	
	
}
