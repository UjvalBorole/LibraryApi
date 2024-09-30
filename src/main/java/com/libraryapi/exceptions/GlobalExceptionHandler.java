package com.libraryapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle ApiExceptions and return a proper response
    @ExceptionHandler(ApiExceptions.class)
    public ResponseEntity<String> handleApiExceptions(ApiExceptions ex) {
        // Create a response with the error message and status 401 Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    // You can add more @ExceptionHandler methods for other exceptions
     // Handle ApiExceptions and return a proper response
     @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
         // Create a response with the error message and status 401 Unauthorized
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
     }
 
}
