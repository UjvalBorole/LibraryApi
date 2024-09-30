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
import com.libraryapi.payloads.ViewsDto;
import com.libraryapi.services.LikeService;
import com.libraryapi.services.ViewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/views")
public class ViewsController {

    @Autowired
    private ViewService viewsService;
    // /api/views/user/{userId}/book/{bookId}
    @PostMapping("/user/{userId}/book/{bookId}")
    public ResponseEntity<ViewsDto>createview(
         @PathVariable Integer userId, @PathVariable Integer bookId
    ){
        ViewsDto viewsDto1 = this.viewsService.createviews(userId, bookId);
        return new ResponseEntity<>(viewsDto1,HttpStatus.CREATED);
    }

    @DeleteMapping("/{viewId}")
    public ApiResponse deleteviews(@PathVariable Integer viewId){
        this.viewsService.deleteviews(viewId);
        return new ApiResponse("Views was Deleted Successfully",true);
    }
    //getAllLikesByBook
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ViewsDto>>getAllViewsByBook(
        @PathVariable Integer bookId
    ){
        List<ViewsDto>viewsDtos = this.viewsService.getAllviewsByBook(bookId);
        return new ResponseEntity<>(viewsDtos,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ViewsDto>>getAllViewsByUser(
        @PathVariable Integer userId
    ){
        List<ViewsDto>viewsDtos = this.viewsService.getAllViewsByUser(userId);
        return new ResponseEntity<>(viewsDtos,HttpStatus.OK);
    }
}

