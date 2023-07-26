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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class IngredientEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Integer id;
    private String name;
    private String measurementUnit;
    private double calories;
    private double carboHydrate;
    private double sugar;
    private double fat;
    private double saturatedFat;
    private double protein;
    
    
    @ManyToMany
    @JoinTable(
    		name = "ingredient_allergen",
    		joinColumns = @JoinColumn(name = "ingredient_id"),
    		inverseJoinColumns = @JoinColumn(name = "allergen_id")
    		)
    private List<AllergenEntity> allergens = new ArrayList<AllergenEntity>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private List <RecipeIngredientRatioEntity> recipeIngredient = new ArrayList <RecipeIngredientRatioEntity>(); 
    
    




	public IngredientEntity(Integer id, String name, String measurementUnit, double calories, double carboHydrate,
			double sugar, double fat, double saturatedFat, double protein, List<AllergenEntity> allergens,
			List<RecipeIngredientRatioEntity> recipeIngredient) {
		super();
		this.id = id;
		this.name = name;
		this.measurementUnit = measurementUnit;
		this.calories = calories;
		this.carboHydrate = carboHydrate;
		this.sugar = sugar;
		this.fat = fat;
		this.saturatedFat = saturatedFat;
		this.protein = protein;
		this.allergens = allergens;
		this.recipeIngredient = recipeIngredient;
	}


	public IngredientEntity() {
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}


	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}


	public double getCalories() {
		return calories;
	}


	public void setCalories(double calories) {
		this.calories = calories;
	}


	public double getCarboHydrate() {
		return carboHydrate;
	}


	public void setCarboHydrate(double carboHydrate) {
		this.carboHydrate = carboHydrate;
	}


	public double getSugar() {
		return sugar;
	}


	public void setSugar(double sugar) {
		this.sugar = sugar;
	}


	public double getFat() {
		return fat;
	}


	public void setFat(double fat) {
		this.fat = fat;
	}


	public double getSaturatedFat() {
		return saturatedFat;
	}


	public void setSaturatedFat(double saturatedFat) {
		this.saturatedFat = saturatedFat;
	}


	public double getProtein() {
		return protein;
	}


	public void setProtein(double protein) {
		this.protein = protein;
	}


	
	
    
	 public List<AllergenEntity> getAllergens() {
		return allergens;
	}


	public void setAllergens(List<AllergenEntity> allergens) {
		this.allergens = allergens;
	}


	public List<RecipeIngredientRatioEntity> getRecipeIngredient() {
		return recipeIngredient;
	}


	public void setRecipeIngredient(List<RecipeIngredientRatioEntity> recipeIngredient) {
		this.recipeIngredient = recipeIngredient;
	}


	@Override
	    public String toString() {
	        return "IngredientEntity{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                ", measurementUnit='" + measurementUnit + '\'' +
	                ", calories=" + calories +
	                ", carboHydrate=" + carboHydrate +
	                ", sugar=" + sugar +
	                ", fat=" + fat +
	                ", saturatedFat=" + saturatedFat +
	                ", protein=" + protein +
	                ", allergens=" + allergens +
	                '}';
	    }
    
    
}
