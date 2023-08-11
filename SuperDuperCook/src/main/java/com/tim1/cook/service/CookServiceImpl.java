package com.tim1.cook.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tim1.cook.entities.CookEntity;
import com.tim1.cook.entities.RoleEntity;
import com.tim1.cook.repositories.CookRepository;
import com.tim1.cook.repositories.RoleRepository;
import com.tim1.cook.utils.Encryption;

@Service
public class CookServiceImpl implements CookService {

	@Autowired
	private CookRepository cookRepository;
	@Autowired private RoleRepository roleRepository;

	@Override
	public CookEntity createCook(CookEntity newCook) {
		String username = newCook.getUsername();
		String password = Encryption.getEncodedPassword(newCook.getPassword());
		RoleEntity role = roleRepository.findById(2).get();

		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Username and password are required fields.");
		}

		if (cookRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("Username already exists.");
		}

		CookEntity cook = new CookEntity();
		cook.setUsername(username);
		cook.setPassword(password);
		cook.setRole(role);

		return cookRepository.save(cook);
	}

	@Override
	public Iterable<CookEntity> getAllCooks() {
		Iterable<CookEntity> cooks = cookRepository.findAll();
		for (CookEntity cook : cooks) {
			if (!isUsernameUnique(cook.getUsername(), cook.getId())) {
				throw new NonUniqueUsernameException("Non-unique username found: " + cook.getUsername());
			}
		}
		return cooks;
	}

	public boolean isUsernameUnique(String username, Integer id) {
		CookEntity existingCook = cookRepository.findByUsername(username);
		return existingCook == null || existingCook.getId().equals(id);
	}

	public class NonUniqueUsernameException extends RuntimeException {
		public NonUniqueUsernameException(String message) {
			super(message);
		}
	}

	@Override
	public CookEntity updateCook(Integer id, CookEntity updatedCook) {
		CookEntity cook = cookRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Cook with ID " + id + " not found."));

		if (cookRepository.existsByUsernameAndIdNot(updatedCook.getUsername(), id)) {
			throw new IllegalArgumentException("Username already exists.");
		}

		cook.setUsername(updatedCook.getUsername());
		cook.setPassword(updatedCook.getPassword());

		return cookRepository.save(cook);
	}

	public class CookNotFoundException extends RuntimeException {
		public CookNotFoundException(String message) {
			super(message);
		}
	}

	@Override
	public CookEntity findCookById(Integer id) {
		return cookRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Cook with ID: " + id + " does not exist."));
	}

	@Override
	public void deleteCook(Integer id) {
		Optional<CookEntity> cookOptional = cookRepository.findById(id);
		if (cookOptional.isEmpty()) {
			throw new CookNotFoundException("Cook with ID " + id + " not found.");
		}

		CookEntity admin = cookOptional.get();
		cookRepository.delete(admin);
	}

	@Override
	public CookEntity findCookByUsername(String username) {
		return cookRepository.findByUsername(username);
	}

}
