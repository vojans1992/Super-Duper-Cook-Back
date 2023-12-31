package com.tim1.cook.entities;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="cook")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class CookEntity extends UserEntity{
	
	@JsonIgnore
	@OneToMany(mappedBy = "author", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<RecipeEntity> myRecipes = new ArrayList<RecipeEntity>();
	
	public CookEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<RecipeEntity> getMyRecipes() {
		return myRecipes;
	}

	public void setMyRecipes(List<RecipeEntity> recipes) {
		this.myRecipes = recipes;
	}

}

