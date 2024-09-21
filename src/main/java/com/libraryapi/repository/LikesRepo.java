package com.libraryapi.repository;

import java.util.List;

import org.modelmapper.internal.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryapi.entities.Book;
import com.libraryapi.entities.Likes;
import com.libraryapi.entities.User;

public interface LikesRepo extends  JpaRepository<Likes,Integer>{
    List<Likes>findByBook(Book book);
    Optional<Likes> findByUserAndBook(User user, Book book);
}
