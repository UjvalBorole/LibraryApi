package com.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryapi.entities.Role;

public interface RoleRepo extends JpaRepository<Role,Integer>{
    
}
