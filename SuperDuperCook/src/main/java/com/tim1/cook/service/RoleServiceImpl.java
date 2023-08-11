package com.tim1.cook.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tim1.cook.entities.RoleEntity;
import com.tim1.cook.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	public RoleRepository roleRepository;
	
	
	@Override
	public RoleEntity createRole(RoleEntity newrole) {
		RoleEntity role = new RoleEntity();
		role.setName(newrole.getName());
		roleRepository.save(role);		
		return role;
	}	
}
