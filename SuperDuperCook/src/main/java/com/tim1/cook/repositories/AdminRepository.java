package com.tim1.cook.repositories;
import org.springframework.data.repository.CrudRepository;
import com.tim1.cook.entities.AdminEntity;

public interface AdminRepository extends CrudRepository<AdminEntity, Integer> {

	boolean existsByUsernameAndIdNot(String username, Integer id);

	boolean existsByUsername(String username);

	AdminEntity findByUsername(String username);
}
