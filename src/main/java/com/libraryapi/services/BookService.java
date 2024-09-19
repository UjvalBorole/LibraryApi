package com.libraryapi.services;

import java.util.List;

import com.libraryapi.payloads.BookDto;
import com.libraryapi.payloads.BookResponse;

public interface BookService {
    //create
	BookDto createBook(BookDto bookDto,Integer userId,Integer categoryId);
	
	//update
	BookDto updateBook(BookDto bookDto,Integer bookId);
	
	//delete
	void deleteBook(Integer bookId);
	
	//get all Books
	BookResponse getAllBook(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//get single Book
	BookDto getBookById(Integer bookId);
	
	//get all Books by category
	List<BookDto>getBooksByCategory(Integer categoryId);
	
	//get all Books by user
	List<BookDto>getBooksByUser(Integer userId);
	
	//search Books
	List<BookDto>searchBooks(String keyword);
}
