package com.libraryapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryapi.entities.Book;
import com.libraryapi.entities.Category;
import com.libraryapi.entities.User;

public interface BookRepo extends JpaRepository<Book,Integer>{
    List<Book>findByUser(User user);

    List<Book>findByCategory(Category category);

    List<Book>findByBookTitleContaining(String keyword);
}
