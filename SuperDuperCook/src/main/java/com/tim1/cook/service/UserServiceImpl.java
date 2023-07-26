package com.tim1.cook.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tim1.cook.entities.RoleEntity;
import com.tim1.cook.entities.UserEntity;
import com.tim1.cook.repositories.RoleRepository;
import com.tim1.cook.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;	

	
	@Override
	public UserEntity createUser(UserEntity newuser) {		
		UserEntity user = new UserEntity();
		user.setUsername(newuser.getUsername());
		user.setPassword(newuser.getPassword());
		userRepository.save(user);
		return user;
	}

	@Override
	public UserEntity updateUser(Integer id, UserEntity updatedUser) {
		UserEntity user = userRepository.findById(id).get();		
		user.setUsername(updatedUser.getUsername());
		user.setPassword(updatedUser.getPassword());
		userRepository.save(user);
		return user;
	}

	@Override
	public UserEntity addRoleToUser(Integer id, Integer roleId) {		
		UserEntity user = userRepository.findById(id).get();
		RoleEntity role = roleRepository.findById(roleId).get();		
		user.setRole(role);
		userRepository.save(user);
		return user;
	}
	
}
