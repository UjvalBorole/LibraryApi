package com.libraryapi.services.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libraryapi.entities.Category;
import com.libraryapi.exceptions.ResourceNotFoundException;
import com.libraryapi.payloads.CategoryDto;
import com.libraryapi.repository.CategoryRepo;
import com.libraryapi.services.CategoryService;


@Service
public class CategoryServiceImp implements CategoryService{
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Category fetchCategory(Integer categoryId){
		return this.categoryRepo.findById(categoryId)
		.orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
	}

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto,Category.class);
		Category resp = this.categoryRepo.save(category);
		return this.modelMapper.map(resp,CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId) {
		Category category = fetchCategory(categoryId);
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category resp = this.categoryRepo.save(category);
		return this.modelMapper.map(resp, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		this.categoryRepo.delete(fetchCategory(categoryId));
		
	}

	@Override
	public CategoryDto get(Integer categoryId) {
		Category category = fetchCategory(categoryId);
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category>categories = this.categoryRepo.findAll();
		List<CategoryDto>collect = categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return collect;
	}
}
