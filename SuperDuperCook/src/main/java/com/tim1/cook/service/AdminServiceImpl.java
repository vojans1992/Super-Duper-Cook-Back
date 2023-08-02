package com.tim1.cook.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim1.cook.entities.AdminEntity;
import com.tim1.cook.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	public AdminRepository adminRepository;

	@Override
	public AdminEntity createAdmin(AdminEntity newAdmin) {
		if (newAdmin.getUsername() == null || newAdmin.getUsername().isEmpty() || newAdmin.getPassword() == null
				|| newAdmin.getPassword().isEmpty()) {
			throw new IllegalArgumentException("Username and password are required fields.");
		}

		if (adminRepository.existsByUsername(newAdmin.getUsername())) {
			throw new IllegalArgumentException("Username already exists.");
		}

		return adminRepository.save(newAdmin);
	}

	@Override
	public AdminEntity updateAdmin(Integer id, AdminEntity updatedAdmin) {
		AdminEntity admin = adminRepository.findById(id).get();
		if (admin == null) {
			throw new NoSuchElementException("Admin with ID " + id + " not found.");
		}
		admin.setUsername(updatedAdmin.getUsername());
		admin.setPassword(updatedAdmin.getPassword());
		adminRepository.save(admin);
		return admin;
	}
}
