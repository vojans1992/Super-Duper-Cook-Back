package com.tim1.cook.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.tim1.cook.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	public List<UserEntity> findAll();

	public List<UserEntity> findById(String id);

	public UserEntity findByUsername(String username);

	public boolean existsByUsername(String username);

	public boolean existsByUsernameAndIdNot(String username, Integer id);

}
