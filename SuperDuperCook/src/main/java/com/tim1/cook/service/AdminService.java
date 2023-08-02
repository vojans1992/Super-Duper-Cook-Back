package com.tim1.cook.service;
import com.tim1.cook.entities.AdminEntity;

public interface AdminService {

	public AdminEntity createAdmin(AdminEntity newAdmin);
	public AdminEntity updateAdmin(Integer id, AdminEntity updatedAdmin);
}
