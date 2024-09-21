package com.libraryapi.repository;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryapi.entities.Printable;
import com.libraryapi.entities.User;

public interface PrintableRepo extends JpaRepository<Printable,Integer>{
    List<Printable>findByUser(User user);
}
