package com.tim1.cook.repositories;
import org.springframework.data.repository.CrudRepository;
import com.tim1.cook.entities.CookEntity;


public interface CookRepository extends CrudRepository <CookEntity, Integer>{
}
