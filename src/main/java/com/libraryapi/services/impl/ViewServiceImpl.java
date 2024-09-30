package com.libraryapi.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import com.libraryapi.entities.Book;
import com.libraryapi.entities.User;
import com.libraryapi.entities.Views;
import com.libraryapi.exceptions.ResourceNotFoundException;
import com.libraryapi.payloads.BookDto;
import com.libraryapi.payloads.UserDto;
import com.libraryapi.payloads.ViewsDto;
import com.libraryapi.repository.ViewsRepo;
import com.libraryapi.services.ViewService;
@Service
public class ViewServiceImpl implements ViewService{
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ViewsRepo viewsRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BookServiceImpl bookServiceImpl;

    public boolean isUserBook(User user, Book book) {
        return ((List<Views>) viewsRepo.findByUserAndBook(user,book)).isEmpty();
    }
    @Override
    public ViewsDto createviews(Integer userId, Integer bookId) {
        User user = this.userServiceImpl.fetchUser(userId);
        Book book = this.bookServiceImpl.fetchBook(bookId);
        Views existingView = viewsRepo.findByUserAndBook(user, book);
        if (existingView != null) {
            viewsRepo.delete(existingView);
        }
    
        Views view = new Views();
        view.setBook(book);
        view.setUser(user);
        Views view1 = this.viewsRepo.save(view);
        ViewsDto viewsDto =this.modelMapper.map(view1,ViewsDto.class);
        BookDto bookDto = this.bookServiceImpl.getBookById(bookId);
        viewsDto.setBook(bookDto);
        return viewsDto;
    }

    @Override
    public void deleteviews(Integer viewId) {
        Views view = this.viewsRepo.findById(viewId)
                .orElseThrow(() -> new ResourceNotFoundException("view", "viewId", viewId));

        this.viewsRepo.delete(view);    
    }

    @Override
    public List<ViewsDto> getAllviewsByBook(Integer bookId) {
        Book book = this.bookServiceImpl.fetchBook(bookId);
        List<Views>views = this.viewsRepo.findByBook(book);
        List<ViewsDto> viewsDtos =  views.stream().map((view) -> this.modelMapper.map(view, ViewsDto.class)).collect(Collectors.toList());
        return viewsDtos;
    }

    @Override
    public List<ViewsDto> getAllViewsByUser(Integer userId) {
        User user = this.userServiceImpl.fetchUser(userId);
        List<Views>views = this.viewsRepo.findByUser(user);
        List<ViewsDto> viewsDtos =  views.stream().map((view) -> this.modelMapper.map(view, ViewsDto.class)).collect(Collectors.toList());
        for(ViewsDto v:viewsDtos){
            Integer i = v.getBook().getBookId();
            BookDto booksDto = this.bookServiceImpl.getBookById(i);
            v.setBook(booksDto);
        }
        return viewsDtos;
    }

    @Override
    public List<ViewsDto> getAllviews() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllLikes'");
    }

}
