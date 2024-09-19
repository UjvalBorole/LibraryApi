package com.libraryapi.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libraryapi.entities.Book;
import com.libraryapi.entities.Comment;
import com.libraryapi.entities.User;
import com.libraryapi.exceptions.ResourceNotFoundException;
import com.libraryapi.payloads.CommentDto;
import com.libraryapi.repository.BookRepo;
import com.libraryapi.repository.CommentRepo;
import com.libraryapi.repository.UserRepo;
import com.libraryapi.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private BookRepo bookRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer bookId,Integer userId) {
		Book book = this.bookRepo.findById(bookId)
				.orElseThrow(()->new ResourceNotFoundException("Book","bookId",bookId));
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","userId",userId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setUser(user);
		comment.setBook(book);
		Comment savedComment =this.commentRepo.save(comment);
		
		return  this.modelMapper.map(savedComment, CommentDto.class); 
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));
			this.commentRepo.delete(comment);
	}

}
