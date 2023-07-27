package com.tim1.cook.service;

import java.util.List;

import com.tim1.cook.entities.AllergenEntity;


public interface AllergenService {

	public AllergenEntity createNewAllergen(AllergenEntity newAllergen);
	
	public List<AllergenEntity> getAllAllergens();
	public AllergenEntity getAllergenById(Integer id);
	
	public AllergenEntity updateAllergen(Integer id, AllergenEntity allergen);

	public void deleteAllergenById(Integer id);
}
