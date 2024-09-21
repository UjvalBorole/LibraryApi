package com.libraryapi.services;

import com.libraryapi.entities.Content;
import com.libraryapi.payloads.ContentDto;
import com.libraryapi.payloads.ContentResponse;

import java.util.*;

public interface ContentService {
    //create
    ContentDto createContent(ContentDto contentDto,Integer bookId);
    
    //update
    ContentDto updateContent(ContentDto contentDto,Integer contentId);
    //delete
    void deleteContent(Integer contentId);

    //get all content
    List<ContentDto>getAllContentsByBookId(Integer bookId);

    Content fetchContent(Integer contentId);

    ContentDto getContentByBookAndPageNo(Integer bookId,Integer pageno);

    ContentDto getContentByBookAndContentTitleContaining(Integer bookId,String keyword);
}
