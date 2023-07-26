package com.tim1.cook.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim1.cook.entities.AdminEntity;
import com.tim1.cook.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	public AdminRepository adminRepository;


	@Override
	public AdminEntity createAdmin(AdminEntity newadmin) {
		AdminEntity admin = new AdminEntity();
		admin.setUsername(newadmin.getUsername());
		admin.setPassword(newadmin.getPassword());
		adminRepository.save(newadmin);
		return admin;
	}

	@Override
	public AdminEntity updateAdmin(Integer id, AdminEntity updatedAdmin) {
		AdminEntity admin = adminRepository.findById(id).get();
		admin.setUsername(updatedAdmin.getUsername());
		admin.setPassword(updatedAdmin.getPassword());
		adminRepository.save(admin);
		return admin;
	}	
}
