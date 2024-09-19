package com.libraryapi.repository;

import org.hibernate.query.Page;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

import com.libraryapi.entities.Book;
import com.libraryapi.entities.Content;
import java.util.List;


public interface ContentRepo extends JpaRepository<Content,Integer>{
    List<Content> findByBook(Book book);
    Content findByBookAndPageNo(Book book,Integer pageNo);
    Content findByBookAndContentTitleContaining(Book book,String keyword);
}
