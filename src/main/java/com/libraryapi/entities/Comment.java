package com.libraryapi.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="comments")
@Getter
@Setter
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String content;
	
	@ManyToOne
	private Book book;
	
	@ManyToOne
	@JoinColumn(name="user_ID")
	private User user;
}
