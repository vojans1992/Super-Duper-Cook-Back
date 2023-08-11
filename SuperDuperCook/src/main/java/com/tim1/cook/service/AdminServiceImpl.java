package com.tim1.cook.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tim1.cook.entities.AdminEntity;
import com.tim1.cook.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	public AdminRepository adminRepository;

	@Override
	public Iterable<AdminEntity> getAllAdmins() {
		Iterable<AdminEntity> admins = adminRepository.findAll();
		for (AdminEntity admin : admins) {
			if (!isUsernameUnique(admin.getUsername(), admin.getId())) {
				throw new NonUniqueUsernameException("Non-unique username found: " + admin.getUsername());
			}
		}
		return admins;
	}

	public boolean isUsernameUnique(String username, Integer id) {
		AdminEntity existingAdmin = adminRepository.findByUsername(username);
		return existingAdmin == null || existingAdmin.getId().equals(id);
	}

	public class NonUniqueUsernameException extends RuntimeException {
		public NonUniqueUsernameException(String message) {
			super(message);
		}
	}

	@Override
	public AdminEntity createAdmin(AdminEntity admin) {
		String username = admin.getUsername();
		String password = admin.getPassword();

		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Username and password are required fields.");
		}

		if (adminRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("Username already exists.");
		}

		AdminEntity newAdmin = new AdminEntity();
		newAdmin.setUsername(username);
		newAdmin.setPassword(password);

		return adminRepository.save(newAdmin);
	}

	@Override
	public AdminEntity updateAdmin(Integer id, AdminEntity updatedAdmin) {
		AdminEntity admin = adminRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Admin with ID " + id + " not found."));

		if (adminRepository.existsByUsernameAndIdNot(updatedAdmin.getUsername(), id)) {
			throw new IllegalArgumentException("Username already exists.");
		}

		admin.setUsername(updatedAdmin.getUsername());
		admin.setPassword(updatedAdmin.getPassword());

		return adminRepository.save(admin);
	}

	public class AdminNotFoundException extends RuntimeException {
		public AdminNotFoundException(String message) {
			super(message);
		}
	}

	@Override
	public void deleteAdmin(Integer id) {
		Optional<AdminEntity> adminOptional = adminRepository.findById(id);
		if (adminOptional.isEmpty()) {
			throw new AdminNotFoundException("Admin with ID " + id + " not found.");
		}

		AdminEntity admin = adminOptional.get();
		adminRepository.delete(admin);
	}

}
