package com.libraryapi.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libraryapi.payloads.ApiResponse;
import com.libraryapi.payloads.BookDto;
import com.libraryapi.payloads.LikesDto;
import com.libraryapi.services.LikeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/like")
public class LikesController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/user/{userId}/book/{bookId}")
    public ResponseEntity<LikesDto>createLike(
         @PathVariable Integer userId, @PathVariable Integer bookId
    ){
        LikesDto likesDto1 = this.likeService.createLikes(userId, bookId);
        return new ResponseEntity<>(likesDto1,HttpStatus.CREATED);
    }

    @DeleteMapping("/{likeId}")
    public ApiResponse deleteLikes(@PathVariable Integer likeId){
        this.likeService.deleteLikes(likeId);
        return new ApiResponse("Liked was Deleted Successfully",true);
    }
    //getAllLikesByBook
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<LikesDto>>getAllLikesByBook(
        @PathVariable Integer bookId
    ){
        List<LikesDto>likesDtos = this.likeService.getAllLikesByBook(bookId);
        return new ResponseEntity<>(likesDtos,HttpStatus.OK);
    }

        //getLikeByUserAndBook
    @GetMapping("/user/{userId}/book/{bookId}")
    public ResponseEntity<LikesDto>getLikeByUserAndBook(
         @PathVariable Integer userId, @PathVariable Integer bookId
    ){
        LikesDto likesDto1 = this.likeService.getLikeByUserAndBook(userId, bookId);
        return new ResponseEntity<>(likesDto1,HttpStatus.OK);
    }

    //getLikeByUser
    // /api/like/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikesDto>>getLikeByUser(
         @PathVariable Integer userId
    ){
        List<LikesDto>likesDtos = this.likeService.getAllLikesByUser(userId);
        return new ResponseEntity<>(likesDtos,HttpStatus.OK);
    }
}
