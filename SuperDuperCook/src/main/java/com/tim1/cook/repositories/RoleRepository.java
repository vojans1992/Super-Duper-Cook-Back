package com.tim1.cook.repositories;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.tim1.cook.entities.RoleEntity;


public interface RoleRepository extends CrudRepository <RoleEntity, Integer>{

	List<RoleEntity> findByName(String r);
}
