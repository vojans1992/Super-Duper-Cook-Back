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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="allergen")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AllergenEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "allergen_id")
	private Integer id;
	
	@NotNull(message = "Allergen name must be provided.")
	@Column(name= "allergen_name")
	private String name;
	
	@NotNull(message = "Allergen icon must be provided.")
	@Column(name="allergen_icon")
	private String icon;
	
	@Version 
	private Integer version;
	
	
	@ManyToMany(mappedBy =  "allergens")
	List<IngredientEntity> ingredients= new ArrayList<IngredientEntity>();
	
	
	@ManyToMany(mappedBy =  "allergens")
	List<UserEntity> users= new ArrayList<UserEntity>();
	

	public AllergenEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
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


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public List<UserEntity> getUsers() {
		return users;
	}


	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}
	
	
	
	

}
