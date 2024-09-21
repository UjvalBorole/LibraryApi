package com.libraryapi.config;



import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.libraryapi.security.CustomUserDetailService;
import com.libraryapi.security.JWTAuthenticationFilter;

import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableWebMvc//this is used for swagger
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	public static final String[] PUBLIC_URLS= {
			"/api/auth/**",
            // "/api/printable/",
			// "/api/user/**",
            // "/api/categories/**",
            // "/api/book/**",
            // "/api/content/**",
            // "/api/like/**",
            
	};
	
	@Autowired
    private  CustomUserDetailService customUserDetailService;
	
//	@Autowired
//	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint; 
	
	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
            	.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                	.requestMatchers(PUBLIC_URLS).permitAll()
                	// .requestMatchers(HttpMethod.GET).permitAll() 
                    .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                      .authenticationEntryPoint(this.authenticationEntryPoint())  // Handle unauthorized access
                  )
                .sessionManagement(session -> session
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless session management
                  )
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(daoAuthenticationProvider());  // Use custom authentication provid
                
            return http.build();
        
 }

    
//    this code is written in the jwtAuthenticationEntryPoint
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    public DaoAuthenticationProvider daoAuthenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	System.out.println(this.customUserDetailService);
    	provider.setUserDetailsService(this.customUserDetailService);
    	provider.setPasswordEncoder(passwordEncoder());
    	return provider;
    }
}
