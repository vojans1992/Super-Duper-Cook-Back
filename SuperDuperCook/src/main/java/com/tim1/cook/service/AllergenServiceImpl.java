package com.tim1.cook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim1.cook.entities.AllergenEntity;
import com.tim1.cook.repositories.AllergenRepository;

@Service
public class AllergenServiceImpl implements AllergenService {
	
	@Autowired
	private AllergenRepository allergenRepository;

	@Override
	public AllergenEntity createNewAllergen(AllergenEntity newAllergen) {
			return allergenRepository.save(newAllergen);
		}
		
	@Override
	public List<AllergenEntity> getAllAllergens() {
			
		return (List<AllergenEntity>)allergenRepository.findAll();
	}
	
	@Override
	public AllergenEntity getAllergenById(Integer id) {
		
		return allergenRepository.findById(id).get();
		
	}
	
	@Override
	public AllergenEntity updateAllergen(Integer id, AllergenEntity allergen) {
		
		AllergenEntity allergenEntity = getAllergenById(id);
		
		if( allergenEntity != null) {
			allergenEntity.setName(allergen.getName());
			allergenEntity.setIcon(allergen.getIcon());
			
			 return allergenRepository.save(allergenEntity);
			 
		} else {
		
			return null;
		
		}
	}

	@Override
	public void deleteAllergenById(Integer id) {
		
		allergenRepository.deleteById(id);
	
	}

	

	
	


}
