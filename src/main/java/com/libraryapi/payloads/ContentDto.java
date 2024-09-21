package com.libraryapi.payloads;

import com.libraryapi.entities.Book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ContentDto {
    private Integer contentId;

    private String contentTitle;

    @NotNull(message = "Page Number shouldn't be blank")
    private Integer pageNo;

    @NotBlank
    @Size(min=10,message ="Content cannot be blank")
    private String bookContent;

    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String image8;
    private String image9;
    private String image10;
    
    
    private BookDto book;
}
