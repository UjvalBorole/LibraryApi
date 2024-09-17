package com.libraryapi;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LibraryapiApplication implements CommandLineRunner{

@Autowired
private PasswordEncoder passwordEncoder;

@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	
	public static void main(String[] args) {
		SpringApplication.run(LibraryapiApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("Borole@123"));
		try {
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
