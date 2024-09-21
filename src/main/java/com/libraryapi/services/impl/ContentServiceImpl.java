package com.libraryapi.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.parser.ContentRange;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.libraryapi.entities.Book;
import com.libraryapi.entities.Content;
import com.libraryapi.exceptions.ResourceNotFoundException;
import com.libraryapi.payloads.BookDto;
import com.libraryapi.payloads.ContentDto;
import com.libraryapi.payloads.ContentResponse;
import com.libraryapi.payloads.UserDto;
import com.libraryapi.repository.BookRepo;
import com.libraryapi.repository.ContentRepo;
import com.libraryapi.services.BookService;
import com.libraryapi.services.ContentService;
@Service
public class ContentServiceImpl implements ContentService{
    @Autowired
    private BookServiceImpl bookServiceImpl;

    @Autowired
    private ContentRepo contentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookRepo bookRepo;

    @Override
     public Content fetchContent(Integer contentId){
        return this.contentRepo.findById(contentId)
                .orElseThrow(()->new ResourceNotFoundException("Content", "contentId", contentId));
    }

    @Override
    public ContentDto createContent(ContentDto contentDto, Integer bookId) {
        // Find the book by ID
        Book book = this.bookRepo.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("book", "bookId", bookId));
    
        // Map the ContentDto to Content entity
        Content content = this.modelMapper.map(contentDto, Content.class);
        
        // Set the book entity in the content entity
        content.setBook(book);
    
        // Save the content in the repository
        Content savedContent = this.contentRepo.save(content);
    
        // Map the saved content entity back to ContentDto
        ContentDto contentDto1 = this.modelMapper.map(savedContent, ContentDto.class);
    
        // Now map Book to BookDto
        BookDto bookDto = this.modelMapper.map(savedContent.getBook(), BookDto.class);
    
        // Set the BookDto in ContentDto
        contentDto1.setBook(bookDto);
    
        // Return the ContentDto with BookDto mapped
        return contentDto1;
    }
    


    @Override
    public ContentDto updateContent(ContentDto contentDto, Integer contentId) {
       Content content = this.fetchContent(contentId);
       content.setBookContent(contentDto.getBookContent());
       content.setContentTitle(contentDto.getContentTitle());
       content.setImage1(contentDto.getImage1());
       content.setImage2(contentDto.getImage2());
       content.setImage3(contentDto.getImage3());
       content.setImage4(contentDto.getImage4());
       content.setImage5(contentDto.getImage5());
       content.setImage6(contentDto.getImage6());
       content.setImage7(contentDto.getImage7());
       content.setImage8(contentDto.getImage8());
       content.setImage9(contentDto.getImage9());
       content.setImage10(contentDto.getImage10());
        content.setPageNo(contentDto.getPageNo());
        Content savedContent = this.contentRepo.save(content);
        ContentDto contentDto1 = this.modelMapper.map(savedContent, ContentDto.class);
        return contentDto1;
    }

    @Override
    public void deleteContent(Integer contentId) {
       this.contentRepo.delete(this.fetchContent(contentId));
    }

    @Override
    public List<ContentDto> getAllContentsByBookId(Integer bookId) {
       Book book = this.bookServiceImpl.fetchBook(bookId);
       List<Content>contents = this.contentRepo.findByBook(book);
       List<ContentDto>contentDtos = contents.stream().map((content) -> {
            ContentDto contentDto = this.modelMapper.map(content, ContentDto.class);
            BookDto bookDto = this.modelMapper.map(content.getBook(), BookDto.class);
            contentDto.setBook(bookDto);
            return contentDto;
        }).collect(Collectors.toList());
        return contentDtos;
    }

    @Override
    public ContentDto getContentByBookAndPageNo(Integer bookId, Integer pageno) {
        Book book = this.bookServiceImpl.fetchBook(bookId);
       Content content= this.contentRepo.findByBookAndPageNo(book, pageno);
       ContentDto contentDto = this.modelMapper.map(content,ContentDto.class);
       BookDto bookDto = this.modelMapper.map(content.getBook(), BookDto.class);
       contentDto.setBook(bookDto);
       return contentDto;
    }

    @Override
    public ContentDto getContentByBookAndContentTitleContaining(Integer bookId, String keyword) {
        Book book = this.bookServiceImpl.fetchBook(bookId);
        Content content = this.contentRepo.findByBookAndContentTitleContaining(book, keyword);
        ContentDto contentDto = this.modelMapper.map(content,ContentDto.class);
       BookDto bookDto = this.modelMapper.map(content.getBook(), BookDto.class);
       contentDto.setBook(bookDto);
       return contentDto;
    }

}
