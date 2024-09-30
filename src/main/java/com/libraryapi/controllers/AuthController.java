package com.libraryapi.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import com.libraryapi.exceptions.ApiExceptions;
import com.libraryapi.payloads.JWTAuthRequest;
import com.libraryapi.payloads.JWTAuthResponse;
import com.libraryapi.payloads.UserDto;
import com.libraryapi.security.JWTTokenHelper;
import com.libraryapi.services.UserServices;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserServices userService;

	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse>createToken(
		@RequestBody JWTAuthRequest request	
			) throws Exception{
		
		this.authenticate(request.getUsername(),request.getPassword());
		System.out.println(request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JWTAuthResponse  response = new JWTAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JWTAuthResponse>(response,HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
		try {
		this.authenticationManager.authenticate(authenticationToken);
		// System.out.println(username +" " +password);
		}catch(BadCredentialsException e) {
			System.out.println("Invalid Username or Password !");
			throw new ApiExceptions("Invalid Username or Password !");
		}
	}
	
	@PostMapping("/registerUser/{roleId}")
    public ResponseEntity<UserDto>registerUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer roleId){
        UserDto createUser = this.userService.registerNewUser(userDto,roleId);
        return new ResponseEntity<>(createUser,HttpStatus.CREATED);
    }


	@GetMapping("/user")
    public ResponseEntity<Map<String, String>> getUserInfo(OAuth2AuthenticationToken authentication) {
        OAuth2User principal = authentication.getPrincipal();
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("name", name);
        userInfo.put("email", email);

        return ResponseEntity.ok(userInfo);
    }
}
