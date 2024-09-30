package com.libraryapi.services;

import java.util.List;

import com.libraryapi.payloads.LikesDto;

public interface LikeService {
    LikesDto createLikes(Integer userId,Integer bookId);
    void deleteLikes(Integer likeId);
    List<LikesDto>getAllLikesByBook(Integer bookId);
    List<LikesDto>getAllLikes();
     List<LikesDto> getAllLikesByUser(Integer userId) ;
    LikesDto getLikeByUserAndBook(Integer userId, Integer bookId);
}
