package com.libraryapi.payloads;

import com.libraryapi.entities.Book;

import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesDto {
    private Integer likeId;

    
    private UserDto user;

    
    private BookDto book;
}
