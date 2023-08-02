package com.tim1.cook.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tim1.cook.entities.CookEntity;
import com.tim1.cook.repositories.CookRepository;

@Service
public class CookServiceImpl implements CookService {

	@Autowired
	private CookRepository cookRepository;

	@Override
	public CookEntity createCook(CookEntity newCook) {
		if (newCook.getUsername() == null || newCook.getUsername().isEmpty() || newCook.getPassword() == null
				|| newCook.getPassword().isEmpty()) {
			throw new IllegalArgumentException("Username and password are required fields.");
		}

		if (cookRepository.existsByUsername(newCook.getUsername())) {
			throw new IllegalArgumentException("Username already exists.");
		}

		return cookRepository.save(newCook);
	}

	@Override
	public CookEntity updateCook(Integer id, CookEntity updatedCook) {
		CookEntity cook = cookRepository.findById(id).get();
		if (cook == null) {
			throw new NoSuchElementException("Cook with ID " + id + " not found.");
		}		
		cook.setUsername(updatedCook.getUsername());
		cook.setPassword(updatedCook.getPassword());
		cookRepository.save(cook);
		return cook;
	}

	@Override
	public CookEntity findCookById(Integer id) {
		return cookRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Cook with ID: " + id + " does not exist."));
	}

}
