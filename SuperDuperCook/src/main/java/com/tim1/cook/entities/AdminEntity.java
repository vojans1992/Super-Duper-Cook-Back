package com.tim1.cook.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="admin")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminEntity extends UserEntity {

	public AdminEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
