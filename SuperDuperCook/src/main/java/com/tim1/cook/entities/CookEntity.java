package com.tim1.cook.entities;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="cook")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class CookEntity extends UserEntity{
	
	
	public CookEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


}

