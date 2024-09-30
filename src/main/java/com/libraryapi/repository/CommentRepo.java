package com.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.libraryapi.entities.Book;
import com.libraryapi.entities.Comment;


public interface CommentRepo extends JpaRepository<Comment,Integer>{
    List<Comment>findByBook(Book book);

}
