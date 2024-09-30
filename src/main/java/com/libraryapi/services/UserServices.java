package com.libraryapi.services;

import java.util.*;

import com.libraryapi.payloads.UserDto;

public interface UserServices {
    UserDto registerNewUser(UserDto userDto,Integer roleId);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto,Integer userId);

    UserDto getUserByID(Integer userId);
    UserDto getUserByEmail(String email);
    List<UserDto>getAllUsers();

    void deleteUser(Integer userId);
    void changePass(UserDto userDto,Integer userId);
}
