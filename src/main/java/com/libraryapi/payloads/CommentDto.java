package com.libraryapi.payloads;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
public class CommentDto {
	private int id;
	
    @NotBlank
    @Size(min=4,message = "content has atleast 4 letters")
	private String content;
	
    private BookDto book;
	private UserDto user;
	
}
