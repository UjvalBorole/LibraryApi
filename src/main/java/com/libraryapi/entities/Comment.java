package com.libraryapi.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name="comments")
@Getter
@Setter
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String content;

	private Date date;

	@ManyToOne
	private Book book;
	
	@ManyToOne
	@JoinColumn(name="user_ID")
	private User user;
}
