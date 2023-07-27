package com.tim1.cook.repositories;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.tim1.cook.entities.AllergenEntity;

public interface AllergenRepository extends CrudRepository<AllergenEntity, Integer>{
	
	List<AllergenEntity> findByName(String name);
}
