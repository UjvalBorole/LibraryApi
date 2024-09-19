package com.libraryapi.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="Book")
@Getter
@Setter
@NoArgsConstructor
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookId;
	
	@Column(name="title",length=100,nullable=false)
	private String bookTitle;
	
    @Column(name="description", length=10000)
	private String bookdescription;

	@Column(name = "imageName")
	private String bookImageName;
	
	@Column(name = "addedDate")
	private Date bookAddedDate;

    @Column(name = "price")
    private double price;
	

    @Column(name = "isprintable")
    private Boolean isprintable;

	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@OneToMany(mappedBy="book",cascade = CascadeType.ALL)
	private Set<Comment>comments = new HashSet<>();

	@OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
	private Set<Content>contents = new HashSet<>();
}
