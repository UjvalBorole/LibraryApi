package com.libraryapi.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.libraryapi.entities.User;
import com.libraryapi.payloads.UserDto;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Integer bookId;
	
	@NotBlank
	@Size(min=4,message="Title of the book cannot be blank")
	private String bookTitle;
	
	@NotBlank
	@Size(min=10,message= "description of the book cannot be blank")
    private String bookdescription;
    
	private String bookImageName;
	
	private Date bookAddedDate;

    @NotNull(message = "this field not null if there has no price then give this field value as 0")
    private double price;

	@NotNull(message = "Please specify if the book is printable or not")
    private Boolean isprintable;

	
	private UserDto userDto;


	

	// private Set<Comment>comments = new HashSet<>();
}
