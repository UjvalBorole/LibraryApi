package com.libraryapi.config;


import org.springframework.security.config.Customizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.libraryapi.security.CustomUserDetailService;
import com.libraryapi.security.JWTAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

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
            "/oauth2/**",
            
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
        .cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(PUBLIC_URLS).permitAll()
            // .requestMatchers(HttpMethod.GET).permitAll()
            .anyRequest().authenticated()
        )
        .oauth2Login(oauth2 -> oauth2
            .defaultSuccessUrl("http://localhost:5173/home", true)
            .userInfoEndpoint(userInfo -> userInfo
                .userService(this.oAuth2UserService())
            )
        )
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(this.authenticationEntryPoint())
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authenticationProvider(daoAuthenticationProvider());

    return http.build();
}

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return (userRequest) -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            // Fetching email and name from OAuth2User
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            System.out.println(email+" "+name);

            return oAuth2User;
        };
    }
    
//    this code is written in the jwtAuthenticationEntryPoint
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        };
    }

//     @Bean
// public AuthenticationEntryPoint authenticationEntryPoint() {
//     return (request, response, authException) -> {
//         response.setContentType("application/json");
//         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//         response.getOutputStream().println("{ \"error\": \"" + authException.getMessage()  + "\" }");
//     };
// }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //oauth configurations
    

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    public DaoAuthenticationProvider daoAuthenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	// System.out.println(this.customUserDetailService);
    	provider.setUserDetailsService(this.customUserDetailService);
    	provider.setPasswordEncoder(passwordEncoder());
    	return provider;
    }
}

