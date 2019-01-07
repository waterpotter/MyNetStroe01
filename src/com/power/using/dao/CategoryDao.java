package com.power.using.dao;

import java.util.List;

import com.power.using.domian.Category;

public interface CategoryDao {

	void save(Category c);

	List<Category> findAll();

	Category findById(String categoryId);

}
