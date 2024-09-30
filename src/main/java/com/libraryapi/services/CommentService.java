package com.libraryapi.services;

import java.util.List;

import com.libraryapi.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto,Integer bookId,Integer userId);
	List<CommentDto> getAllCommentsByBookId(Integer bookId);
	void deleteComment(Integer commentId);
	
}

