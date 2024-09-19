package com.libraryapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.libraryapi.payloads.ApiResponse;
import com.libraryapi.payloads.ContentDto;
import com.libraryapi.payloads.ContentResponse;
import com.libraryapi.services.ContentService;

@RestController
@RequestMapping("/api/content")
public class ContentControllers {

    @Autowired
    private ContentService contentService;

    @PostMapping("/{bookId}")
    public ResponseEntity<ContentDto>createContent(@RequestBody ContentDto contentDto,@PathVariable Integer bookId){
        ContentDto contentDto1 = this.contentService.createContent(contentDto, bookId);
        return new ResponseEntity<ContentDto>(contentDto1,HttpStatus.CREATED);
    }

    @PutMapping("/{contentId}")
    public ResponseEntity<ContentDto>updateContent(@RequestBody ContentDto contentDto,@PathVariable Integer contentId){
        ContentDto contentDto1 = this.contentService.updateContent(contentDto, contentId);
        return new ResponseEntity<ContentDto>(contentDto1,HttpStatus.CREATED);
    }

    //delete
    @DeleteMapping("/{contentId}")
    public ApiResponse deleteContent(@PathVariable Integer contentId){
        this.contentService.deleteContent(contentId);
        return new ApiResponse("Content Delet Successfully !!!",true);
    }

    // getAllContentsByBookId
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ContentDto>>getAllContentsByBookId(@PathVariable Integer bookId){
        List<ContentDto> contentDtos = this.contentService.getAllContentsByBookId(bookId);
        return new ResponseEntity<>(contentDtos,HttpStatus.OK);
    }

 


    //getContentByBookAndPageNo
    @GetMapping("/book/{bookId}/pageNo/{pageNo}")
    public ResponseEntity<ContentDto>getContentByBookAndPageNo(@PathVariable Integer bookId,@PathVariable Integer pageNo){
        ContentDto contentDto = this.contentService.getContentByBookAndPageNo(bookId, pageNo);
        return new ResponseEntity<>(contentDto,HttpStatus.OK);
    }

    //getContentByBookAndContentTitleContaining
    @GetMapping("/book/{bookId}/keyword/{keyword}")
    public ResponseEntity<ContentDto>getContentByBookAndContentTitleContaining(@PathVariable Integer bookId,@PathVariable String keyword){
        ContentDto contentDto = this.contentService.getContentByBookAndContentTitleContaining(bookId, keyword);
        return new ResponseEntity<>(contentDto,HttpStatus.OK);
    }
}
