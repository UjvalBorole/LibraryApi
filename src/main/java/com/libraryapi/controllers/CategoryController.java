package com.libraryapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import com.libraryapi.payloads.ApiResponse;
import com.libraryapi.payloads.CategoryDto;
import com.libraryapi.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@EnableMethodSecurity(prePostEnabled = true)
public class CategoryController {
    
	@Autowired
	private CategoryService categoryService;
	
	//create
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	
	//update
    @PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto>updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId){
		System.out.println(catId);
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
	
	//delete
    @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Integer catId){
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
	}
	//get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto>getCategory(@PathVariable Integer catId){
		CategoryDto category = this.categoryService.get(catId);
		return new ResponseEntity<CategoryDto>(category,HttpStatus.OK);
	}
	
	//get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>>getAllCategory(){
		List<CategoryDto>categories = this.categoryService.getCategories();
		return new ResponseEntity<List<CategoryDto>>(categories,HttpStatus.OK);
	}
}
