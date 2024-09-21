package com.libraryapi.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewsDto {
    private Integer viewId;

    
    private UserDto user;

    
    private BookDto book;
}
