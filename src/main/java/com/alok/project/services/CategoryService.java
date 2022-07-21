package com.alok.project.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alok.project.payloads.CategoryDto;

public interface CategoryService {

	public CategoryDto createCategory(CategoryDto cateDto);
	
	public CategoryDto updateCategory(CategoryDto cateDto, Integer categoryId);
	
	public void deleteCategory(Integer categoryId);
	
	public CategoryDto getCategory(Integer categoryId);
	
	public List<CategoryDto> getCategories();
	
}
