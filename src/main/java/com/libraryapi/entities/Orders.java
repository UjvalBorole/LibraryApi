package com.libraryapi.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
// @Table(name = "likes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "book_id"})})
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private Date date;
    // Many Likes can belong to one User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Many Likes can be for one Book
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;


}
