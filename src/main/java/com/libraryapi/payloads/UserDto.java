package com.libraryapi.payloads;

import java.util.*;

import org.hibernate.annotations.ManyToAny;

import com.libraryapi.entities.Book;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private Integer userid;

    @NotBlank(message = "Name Cannot be Blank")
    @Size(min=4,message="Size must be greater than 5 letters")
    private String fullName;

    @Email(message = "Email Cannoot be Blank")
    private String email;

    @NotNull
    @NotBlank(message = "Password Cannot be Blank")
    @Size(min = 3,max = 10,message = "Size must be grater than 3 letters and lesser than 10 letter")
    private String password;

    @NotBlank(message = "PhoneNumber  Cannot be Blank")
    @Size(max = 10,message = "Size must be grater than 10 letters")
    private String phonenumber;
    
    private String userimage;

   
    List<BookDto>books = new ArrayList<>();
}
