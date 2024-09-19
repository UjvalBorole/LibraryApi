package com.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryapi.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer>{
    
}
