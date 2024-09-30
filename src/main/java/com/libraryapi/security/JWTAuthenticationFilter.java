package com.libraryapi.security;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		1. get Token
		String requestToken = request.getHeader("Authorization");
		
		//Bearer 232323sdgsg
		// System.out.println("Token "+requestToken);
		
		String username = null;
		
		String token = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			// System.out.println("Token "+token);
			try {
			username = this.jwtTokenHelper.getUsernameFromToken(token);
			}catch(IllegalArgumentException e) {
				// System.out.println("Unable to get Jwt token");
			}catch(ExpiredJwtException e) {
				// System.out.println("Jwt token has expired");
			}catch(MalformedJwtException e) {
				// System.out.println("Invalid jwt");
			}
			
		}else {
			// System.out.println("JWT Token doesn't start with Bearer !!");
		}
		
		//once we get the token, now validate
		if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			//if the token match the below line gives true or false 
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
//				if above line gives true means whole code going on good manner
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}else {
				// System.out.println("Invalid Jwt Token");
			}
		}else {
			// System.out.println("User name is null or Context is null");
		}
		
		
		filterChain.doFilter(request, response);
	}

}
