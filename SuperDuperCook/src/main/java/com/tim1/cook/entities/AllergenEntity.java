package com.tim1.cook.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="allergen")
@JsonIgnoreProperties({"handeler", "hibernateLazyInitializer"})
public class AllergenEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "allergen_id)")
	private Integer id;
	@Column(name= "allergen_name")
	private String name;
	@Column(name="allergen_icon")
	private String icon;
	@Version 
	private Integer version;
	
	
	@ManyToMany(mappedBy =  "allergens")
	List<IngredientEntity> ingredients= new ArrayList<IngredientEntity>();
	
	@ManyToMany(mappedBy =  "allergens")
	List<UserEntity> users= new ArrayList<UserEntity>();
	
	

	public AllergenEntity(Integer id, String name, String icon, Integer version, List<IngredientEntity> ingredients) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.version = version;
		this.ingredients = ingredients;
	}


	public List<IngredientEntity> getIngredients() {
		return ingredients;
	}


	public void setIngredients(List<IngredientEntity> ingredients) {
		this.ingredients = ingredients;
	}


	public AllergenEntity(Integer id, String name, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
	

}
