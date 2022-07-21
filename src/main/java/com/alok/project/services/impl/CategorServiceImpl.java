package com.alok.project.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok.project.entities.Category;
import com.alok.project.exceptions.ResourceNotFoundException;
import com.alok.project.payloads.CategoryDto;
import com.alok.project.repositories.CategoryRepo;
import com.alok.project.services.CategoryService;

@Service
public class CategorServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedcat = this.categoryRepo.save(cat);
		return this.modelMapper.map(addedcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category ", " categoryId ", categoryId));
		cat.setCategoryName(categoryDto.getCategoryName());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedcat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(updatedcat, CategoryDto.class);
		
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", " category id ", categoryId));
		
		this.categoryRepo.delete(cat);
		
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", " category id ", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
//		return null;
	}

	@Override
	public List<CategoryDto> getCategories() {
		// TODO Auto-generated method stub
		List<Category> categories= this.categoryRepo.findAll();
		List<CategoryDto> catedtos= new ArrayList<CategoryDto>();
		for(Category cat: categories) {
			catedtos.add(this.modelMapper.map(cat, CategoryDto.class));
		}
		
		return catedtos;
//		return null;
	}
	
	

	
}
