package com.libraryapi.payloads;


import java.util.*;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class ContentResponse {
	//this is used for the pagination purpose
	private List<ContentDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElement;
	private int totalPages;
	private boolean lastPage;
}
