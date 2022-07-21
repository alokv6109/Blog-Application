package com.alok.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok.project.payloads.ApiResponse;
import com.alok.project.payloads.CategoryDto;
import com.alok.project.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory( @Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createdcat = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdcat, HttpStatus.CREATED);
	}
	
	
	//update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory( @Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId){
		CategoryDto updatedcat = this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<CategoryDto>(updatedcat, HttpStatus.OK);
	}
	
	
	//delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> createCategory(@PathVariable Integer catId){
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted", true), HttpStatus.OK);
	}
	
	//getone
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
		CategoryDto catdto = this.categoryService.getCategory(catId);
		return new ResponseEntity<CategoryDto>(catdto, HttpStatus.OK);
	}
	
	
	
	//getall
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> catdtos = this.categoryService.getCategories();
		return new ResponseEntity<List<CategoryDto>>(catdtos, HttpStatus.OK);
	}

}
