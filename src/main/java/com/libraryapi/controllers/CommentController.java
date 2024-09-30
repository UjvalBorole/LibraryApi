package com.libraryapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import com.libraryapi.payloads.ApiResponse;
import com.libraryapi.payloads.CommentDto;
import com.libraryapi.services.CommentService;

@RestController
@RequestMapping("/api/comment")
@EnableMethodSecurity(prePostEnabled = true)
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	// /api/comment/{bookId}/user/{userId}/comments
	@PostMapping("/{bookId}/user/{userId}/comments")
	public ResponseEntity<CommentDto>createComment(
			@RequestBody CommentDto commentDto,
			@PathVariable Integer bookId,
			@PathVariable Integer userId
			){
		CommentDto comment = this.commentService.createComment(commentDto, bookId,userId);
		return new  ResponseEntity<CommentDto>(comment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse>deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);
	}
	// // getAllCommentsByBookId
	@GetMapping("/{bookId}")
	public ResponseEntity<List<CommentDto>>getAllCommnetsBooks(
			@PathVariable Integer bookId
			){
		List<CommentDto> comment = this.commentService.getAllCommentsByBookId(bookId);
		return new  ResponseEntity<>(comment,HttpStatus.OK);
	}
}
