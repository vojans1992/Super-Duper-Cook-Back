package com.tim1.cook.service;
import com.tim1.cook.entities.CookEntity;

public interface CookService {

	public CookEntity createCook(CookEntity newCook);
	public CookEntity updateCook(Integer id, CookEntity updatedCook);
	public CookEntity findCookById(Integer id);
}
