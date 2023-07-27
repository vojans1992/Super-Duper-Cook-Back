package com.tim1.cook.entities.dto;

import java.util.List;

public class IngredientDTO {
	
	 protected Integer id;
	 protected String name;
	 protected String measurementUnit;
	 protected double calories;
	 protected double carboHydrate;
	 protected double sugar;
	 protected double fat;
	 protected double saturatedFat;
	 protected double protein;
	 protected List<String> allergens;
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
	public List<String> getAllergens() {
		return allergens;
	}
	public void setAllergens(List<String> allergens) {
		this.allergens = allergens;
	}
}
