package com.tim1.cook.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class IngredientEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String measurementUnit;
    private double calories;
    private double carboHydrate;
    private double sugar;
    private double fat;
    private double saturatedFat;
    private double protein;
    
    @OneToOne
    private AllergenEntity allergen;
    
    


	public IngredientEntity(Integer id, String name, String measurementUnit, double calories, double carboHydrate,
			double sugar, double fat, double saturatedFat, double protein, AllergenEntity allergen) {
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
		this.allergen = allergen;
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


	public AllergenEntity getAllergen() {
		return allergen;
	}


	public void setAllergen(AllergenEntity allergen) {
		this.allergen = allergen;
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
	                ", allergen=" + allergen +
	                '}';
	    }
    
    
}
