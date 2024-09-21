package com.libraryapi.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import com.libraryapi.entities.Book;
import com.libraryapi.entities.Likes;
import com.libraryapi.entities.User;
import com.libraryapi.exceptions.ResourceNotFoundException;
import com.libraryapi.payloads.LikesDto;
import com.libraryapi.payloads.UserDto;
import com.libraryapi.repository.LikesRepo;
import com.libraryapi.services.LikeService;

@Service
public class LikeServiceImpl implements LikeService{
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LikesRepo likesRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @Override
    public LikesDto createLikes(Integer userId, Integer bookId) {
        User user = this.userServiceImpl.fetchUser(userId);
        Book book = this.bookServiceImpl.fetchBook(bookId);
    
        Likes likes = new Likes();
        likes.setBook(book);
        likes.setUser(user);
        Likes likes1 = this.likesRepo.save(likes);
        LikesDto likesDto2 =this.modelMapper.map(likes1,LikesDto.class);
        return likesDto2;
    }

    @Override
    public void deleteLikes(Integer likeId) {
        Likes like = this.likesRepo.findById(likeId)
                .orElseThrow(() -> new ResourceNotFoundException("Like", "likeId", likeId));

        this.likesRepo.delete(like);    
    }

    @Override
    public List<LikesDto> getAllLikesByBook(Integer bookId) {
        Book book = this.bookServiceImpl.fetchBook(bookId);
        List<Likes>likes = this.likesRepo.findByBook(book);
        System.out.println("List<Likes>likes "+likes.get(0).getLikeId()+" " +likes.get(0).getBook());
        // List<LikesDto> likesDtos =  likes.stream().map((like) -> {
        //     LikesDto likeDto = this.modelMapper.map(like, LikesDto.class);
        //     UserDto user = this.modelMapper.map(like.getUser(), UserDto.class);
        //     // LikesDto.setUser(user);

        //     return likeDto;
        // }).collect(Collectors.toList());

        List<LikesDto> likesDtos =  likes.stream().map((like) -> this.modelMapper.map(like, LikesDto.class)).collect(Collectors.toList());
        return likesDtos;
    }

    @Override
    public List<LikesDto> getAllLikes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllLikes'");
    }

}
