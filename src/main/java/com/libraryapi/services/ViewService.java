package com.libraryapi.services;

import java.util.List;

import com.libraryapi.payloads.ViewsDto;


public interface ViewService {
    ViewsDto createviews(Integer userId,Integer bookId);
    void deleteviews(Integer viewId);
    List<ViewsDto>getAllviewsByBook(Integer bookId);
    List<ViewsDto>getAllviews();
}
