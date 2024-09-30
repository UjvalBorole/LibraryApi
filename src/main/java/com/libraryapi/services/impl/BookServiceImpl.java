package com.libraryapi.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale.Category;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.libraryapi.entities.Book;
import com.libraryapi.entities.User;
import com.libraryapi.exceptions.ResourceNotFoundException;
import com.libraryapi.payloads.BookDto;
import com.libraryapi.payloads.BookResponse;
import com.libraryapi.payloads.UserDto;
import com.libraryapi.repository.BookRepo;
import com.libraryapi.repository.CategoryRepo;
import com.libraryapi.repository.UserRepo;
import com.libraryapi.services.BookService;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private CategoryServiceImp categoryServiceImp;


    public Book fetchBook(Integer bookId) {
        return this.bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));
    }

    @Override
    public BookDto createBook(BookDto bookDto, Integer userId, Integer categoryId) {
        User user = this.userServiceImpl.fetchUser(userId);
        Book book = this.modelMapper.map(bookDto, Book.class);
        book.setBookImageName("defaultBook.jfif");
        book.setBookAddedDate(new Date());
        book.setCategory(this.categoryServiceImp.fetchCategory(categoryId));
        book.setUser(user);
        Book savedBook = this.bookRepo.save(book);
        user.getBooks().add(savedBook);
        user.setBooks(user.getBooks());
        this.userRepo.save(user);
        BookDto bookDto1 = this.modelMapper.map(savedBook, BookDto.class);
        UserDto user1 = this.modelMapper.map(savedBook.getUser(), UserDto.class);
        bookDto1.setUserDto(user1);
        return bookDto1;
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Integer bookId) {
        Book book = this.fetchBook(bookId);
        book.setBookTitle(bookDto.getBookTitle());
        book.setBookdescription(bookDto.getBookdescription());
        book.setBookImageName(bookDto.getBookImageName());
        book.setIsprintable(bookDto.getIsprintable());
        book.setPrice(bookDto.getPrice());
        Book updatedBook = this.bookRepo.save(book);
        BookDto bookDto1 = this.modelMapper.map(updatedBook, BookDto.class);
        UserDto userDto = this.modelMapper.map(updatedBook.getUser(), UserDto.class);
        bookDto1.setUserDto(userDto);
        return bookDto1;
    }

    @Override
    public void deleteBook(Integer bookId) {
        this.bookRepo.delete(this.fetchBook(bookId));
    }

    @Override
    public BookResponse getAllBook(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Pageable p = (sortDir.equalsIgnoreCase("desc"))
                ? PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending())
                : PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Book> pageBooks = this.bookRepo.findAll(p);
        List<Book> books = pageBooks.getContent();

        // manageing the error of not converting the user into the userDto
        List<BookDto> bookDtos = books.stream().map((book) -> {
            BookDto bookDto = this.modelMapper.map(book, BookDto.class);
            UserDto user = this.modelMapper.map(book.getUser(), UserDto.class);
            bookDto.setUserDto(user);
            return bookDto;
        }).collect(Collectors.toList());

        BookResponse bookResponse = new BookResponse();
        bookResponse.setContent(bookDtos);
        bookResponse.setPageNumber(pageBooks.getNumber());
        bookResponse.setPageSize(pageBooks.getSize());
        bookResponse.setTotalElement(pageBooks.getTotalElements());
        bookResponse.setTotalPages(pageBooks.getTotalPages());
        bookResponse.setLastPage(pageBooks.isLast());
        return bookResponse;
    }

    @Override
    public BookDto getBookById(Integer bookId) {
        Book book = this.fetchBook(bookId);
        BookDto bookDto1 = this.modelMapper.map(book, BookDto.class);
        UserDto userDto = this.modelMapper.map(book.getUser(), UserDto.class);
        bookDto1.setUserDto(userDto);
        return bookDto1;
    }

    @Override
    public List<BookDto> getBooksByCategory(Integer categoryId) {
        List<Book> bookList = this.bookRepo.findByCategory(this.categoryServiceImp.fetchCategory(categoryId));
        List<BookDto> bookDtos = bookList.stream().map((book) -> {
            BookDto bookDto = this.modelMapper.map(book, BookDto.class);
            UserDto user = this.modelMapper.map(book.getUser(), UserDto.class);
            bookDto.setUserDto(user);
            return bookDto;
        }).collect(Collectors.toList());
        return bookDtos;
    }

    @Override
    public List<BookDto> getBooksByUser(Integer userId) {
        List<Book> bookList = this.bookRepo.findByUser(this.userServiceImpl.fetchUser(userId));
        List<BookDto> bookDtos = bookList.stream().map((book) -> {
            BookDto bookDto = this.modelMapper.map(book, BookDto.class);
            UserDto user = this.modelMapper.map(book.getUser(), UserDto.class);
            bookDto.setUserDto(user);
            return bookDto;
        }).collect(Collectors.toList());
        return bookDtos;
    }

    @Override
    public List<BookDto> searchBooks(String keyword) {
        List<Book> books = this.bookRepo.findByBookTitleContaining(keyword);
        List<BookDto> bookDtos = books.stream().map((book) -> {
            BookDto bookDto = this.modelMapper.map(book, BookDto.class);
            UserDto user = this.modelMapper.map(book.getUser(), UserDto.class);
            bookDto.setUserDto(user);
            return bookDto;
        }).collect(Collectors.toList());
        return bookDtos;
    }
}
