package com.libraryapi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.libraryapi.payloads.ApiResponse;
import com.libraryapi.payloads.BookDto;
import com.libraryapi.payloads.BookResponse;
import com.libraryapi.services.BookService;
import com.libraryapi.services.FileService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/book")
@EnableMethodSecurity(prePostEnabled = true)
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private FileService fileService;

    @Value("${Book.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<BookDto>createBook(
        @Valid @RequestBody BookDto bookDto,@PathVariable Integer userId, @PathVariable Integer categoryId
    ){
        BookDto bookDto1 = this.bookService.createBook(bookDto, userId, categoryId);
        return new ResponseEntity<>(bookDto1,HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDto>updateBook(@RequestBody BookDto bookDto,@PathVariable Integer bookId){
        BookDto bookDto1 = this.bookService.updateBook(bookDto, bookId);
        return new ResponseEntity<>(bookDto1,HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ApiResponse deleteBook(@PathVariable Integer bookId){
        this.bookService.deleteBook(bookId);
        return new ApiResponse("Book was Deleted Successfully",true);
    }

    //get all books
    @PreAuthorize("hasRole('USER')")
	@GetMapping("/books")
	public ResponseEntity<BookResponse>getAllPost(
			@RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue="bookId",required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir
			){
            BookResponse bookResponse = this.bookService.getAllBook(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<BookResponse>(bookResponse,HttpStatus.OK);
	}

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto>getBookById(@PathVariable Integer bookId){
        BookDto bookDto = this.bookService.getBookById(bookId);
        return new ResponseEntity<>(bookDto,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BookDto>>getBookByCategory(@PathVariable Integer categoryId){
        List<BookDto>bookDtos = this.bookService.getBooksByCategory(categoryId);
        return new ResponseEntity<>(bookDtos,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookDto>>getBookByUser(@PathVariable Integer userId){
        List<BookDto>bookDtos = this.bookService.getBooksByUser(userId);
        return new ResponseEntity<>(bookDtos,HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<BookDto>>getBookByUser(@PathVariable String keyword){
        List<BookDto>bookDtos = this.bookService.searchBooks(keyword);
        return new ResponseEntity<>(bookDtos,HttpStatus.OK);
    }

     @PostMapping("/image/upload/{bookId}")
    public ResponseEntity<BookDto>uploadUserImage(
        @RequestParam("image") MultipartFile image,
        @PathVariable Integer bookId
    ) throws IOException{
        BookDto bookDto = this.bookService.getBookById(bookId);
        String fileName = this.fileService.uploadImage(path, image);
        bookDto.setBookImageName(fileName);
        BookDto updatebook = this.bookService.updateBook(bookDto, bookId);
        return new ResponseEntity<BookDto>(updatebook,HttpStatus.OK);
    }

    @GetMapping(value="/user/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response
    )throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
    
}
