package com.libraryapi.repository;

import java.util.List;

import org.modelmapper.internal.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryapi.entities.Book;
import com.libraryapi.entities.User;
import com.libraryapi.entities.Views;

public interface ViewsRepo extends  JpaRepository<Views,Integer>{
    List<Views>findByBook(Book book);
    Optional<Views> findByUserAndBook(User user, Book book);
}
