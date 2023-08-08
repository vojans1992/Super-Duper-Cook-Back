package com.tim1.cook.repositories;
import org.springframework.data.repository.CrudRepository;
import com.tim1.cook.entities.CookEntity;


public interface CookRepository extends CrudRepository <CookEntity, Integer>{

	boolean existsByUsername(String username);

	boolean existsByUsernameAndIdNot(String username, Integer id);

	CookEntity findByUsername(String username);
}
