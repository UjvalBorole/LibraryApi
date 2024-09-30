package com.libraryapi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.libraryapi.payloads.ApiResponse;
import com.libraryapi.payloads.UserDto;
import com.libraryapi.services.FileService;
import com.libraryapi.services.UserServices;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@EnableMethodSecurity(prePostEnabled = true)
public class UserController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private FileService fileService;

    @Value("${User.image}")
    private String path;

    @PostMapping("/createUser")
    public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUser = this.userServices.createUser(userDto);
        return new ResponseEntity<>(createUser,HttpStatus.CREATED);
    }
    
  

    @PostMapping("/updateUser/{userId}") 
    public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
        UserDto updateUser = this.userServices.updateUser(userDto, userId);
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }
// /api/user/user/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto>getUserById(@PathVariable Integer userId){
        UserDto  userDto = this.userServices.getUserByID(userId);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto>getUserByEmail(@PathVariable String email){
        UserDto  userDto = this.userServices.getUserByEmail(email);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>>getAllUsers(){
        List<UserDto>users = this.userServices.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        this.userServices.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
    }

    @PostMapping("/changePass/{userId}")
    public ResponseEntity<ApiResponse>  changePassword(@RequestBody UserDto userDto, @PathVariable Integer userId){
        this.userServices.changePass(userDto, userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Password has been changed Successfuly",true),HttpStatus.OK);
    } 

    @PostMapping("/image/upload/{userId}")
    public ResponseEntity<UserDto>uploadUserImage(
        @RequestParam("image") MultipartFile image,
        @PathVariable Integer userId
    ) throws IOException{
        UserDto userDto = this.userServices.getUserByID(userId);
        String fileName = this.fileService.uploadImage(path, image);
        userDto.setUserimage(fileName);
        UserDto updateUser = this.userServices.updateUser(userDto, userId);
        return new ResponseEntity<UserDto>(updateUser,HttpStatus.OK);
    }

    @GetMapping(value="/user/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response
    )throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
