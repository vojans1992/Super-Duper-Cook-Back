package com.tim1.cook.service;

import com.tim1.cook.entities.AdminEntity;

public interface AdminService {

	public Iterable<AdminEntity> getAllAdmins();

	public AdminEntity createAdmin(AdminEntity admin);

	public AdminEntity updateAdmin(Integer id, AdminEntity updatedAdmin);

	public void deleteAdmin(Integer id);

}
