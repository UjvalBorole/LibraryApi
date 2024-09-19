package com.libraryapi.payloads;
import java.util.*;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class BookResponse {
	//this is used for the pagination purpose
	private List<BookDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElement;
	private int totalPages;
	private boolean lastPage;
}
