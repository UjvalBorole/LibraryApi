package com.libraryapi.repository;
import java.util.*;

import org.modelmapper.internal.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryapi.entities.Book;
import com.libraryapi.entities.Orders;
import com.libraryapi.entities.User;

public interface OrderRepo extends  JpaRepository<Orders,Integer>{
    List<Orders>findByUser(User user);
    Orders findByUserAndBook(User user, Book book);
}