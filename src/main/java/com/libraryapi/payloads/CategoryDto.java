package com.libraryapi.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	private Integer categoryId;
	
	@NotBlank
	@Size(min = 4,message="this value is greater than 4 letters")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10,message="this value is greater than 4 letters")
	private String categoryDescription;
}
