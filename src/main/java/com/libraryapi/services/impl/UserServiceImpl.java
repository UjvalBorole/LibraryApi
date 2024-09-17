package com.libraryapi.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.libraryapi.entities.Role;
import com.libraryapi.entities.User;
import com.libraryapi.exceptions.ResourceNotFoundException;
import com.libraryapi.payloads.UserDto;
import com.libraryapi.repository.RoleRepo;
import com.libraryapi.repository.UserRepo;
import com.libraryapi.services.UserServices;

@Service
public class UserServiceImpl implements UserServices{
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setUserimage("default.jpeg");
        User savedUSer = this.userRepo.save(user);
        return this.modelMapper.map(savedUSer, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto,Integer userId) {
      User user =  this.userRepo.findById(userId)
       .orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
       user.setFullName(userDto.getFullName());
       user.setEmail(userDto.getEmail());
       user.setPhonenumber(userDto.getPhonenumber());
       user.setUserimage(userDto.getUserimage());
       User updated = this.userRepo.save(user);

       return this.modelMapper.map(updated, UserDto.class);
    }

    @Override
    public UserDto getUserByID(Integer userId) {
        User user =  this.userRepo.findById(userId)
        .orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
       List<User>users = this.userRepo.findAll();
       List<UserDto>usersDtos = users.stream().map((user)->this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
       return usersDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user =  this.userRepo.findById(userId)
        .orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

    @Override
    public void changePass(UserDto userDto,Integer userId) {
        User user =  this.userRepo.findById(userId)
        .orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        this.userRepo.save(user);
    }

    @Override
    public UserDto registerNewUser(UserDto userDto,Integer roleId) {
        User user = this.modelMapper.map(userDto,User.class);

        //encode the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setUserimage("default.jpeg");
        //roles
        Role role = this.roleRepo.findById(roleId).get();
        
        user.getRoles().add(role);
        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser,UserDto.class);
    }

}
