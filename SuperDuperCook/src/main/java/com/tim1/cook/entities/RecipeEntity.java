package com.tim1.cook.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="recipe")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class RecipeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "recipe_id")
	private Integer id;
	
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
	
	@NotNull(message = "Recipe must have an author.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name= "author", nullable = false)
	private CookEntity author;
	
	@JsonIgnore
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<RecipeIngredientRatioEntity> recipeIngredientRatios = new ArrayList<RecipeIngredientRatioEntity>();

	public RecipeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	
	public CookEntity getAuthor() {
		return author;
	}

	public void setAuthor(CookEntity author) {
		this.author = author;
	}

	public List<RecipeIngredientRatioEntity> getRecipeIngredientRatios() {
		return recipeIngredientRatios;
	}

	public void setRecipeIngredientRatios(List<RecipeIngredientRatioEntity> recipeIngredientRatios) {
		this.recipeIngredientRatios = recipeIngredientRatios;
	}
	
	
}
