package com.libraryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.libraryapi.payloads.ApiResponse;
import com.libraryapi.payloads.PrintableDto;
import com.libraryapi.services.PrintableService;

@RestController
@RequestMapping("/api/printable")
@EnableMethodSecurity(prePostEnabled = true)
public class PrintableController {
    @Autowired
    private PrintableService printableService;

    // createPrintable
    @PostMapping("/user/{userId}/book/{bookId}")
    public ResponseEntity<PrintableDto>createPrintable(
        @RequestBody PrintableDto printableDto,
         @PathVariable Integer userId, @PathVariable Integer bookId
    ){
        PrintableDto printableDto1 = this.printableService.createPrintable(userId, bookId, printableDto);
        return new ResponseEntity<>(printableDto1,HttpStatus.CREATED);
    }

    // updatePrintable
    @PutMapping("/{printId}")
    public ResponseEntity<PrintableDto>updatePrintable(@RequestBody PrintableDto printableDto,@PathVariable Integer printId){
        PrintableDto printableDto2 = this.printableService.updatePrintable(printId, printableDto);
        return new ResponseEntity<>(printableDto2,HttpStatus.OK);
    }


    // updatePrintableByAdmin
    @PreAuthorize("hasRole('ADMIN') or hasRole('AUTHOR')")
    @PutMapping("/admin/{printId}")
    public ResponseEntity<PrintableDto>updatePrintableByAdmin(@RequestBody PrintableDto printableDto,@PathVariable Integer printId){
        PrintableDto printableDto2 = this.printableService.updatePrintableByAdmin(printId, printableDto);
        return new ResponseEntity<>(printableDto2,HttpStatus.OK);
    }

    // deletePrintable
    @DeleteMapping("/{printId}")
    public ApiResponse deletePrintable(@PathVariable Integer printId){
        this.printableService.deletePrintable(printId);
        return new ApiResponse("Printable was Deleted Successfully",true);
    }

    // getPrintableById
    @GetMapping("/{printId}")
    public ResponseEntity<PrintableDto>getPrintableById(@PathVariable Integer printId){
        PrintableDto printableDto2 = this.printableService.getPrintableById(printId);
        return new ResponseEntity<>(printableDto2,HttpStatus.OK);
    }

    // getPrintableByUser
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PrintableDto>>getPrintableByUser(@PathVariable Integer userId){
        List<PrintableDto>printDtos = this.printableService.getPrintableByUser(userId);
        return new ResponseEntity<>(printDtos,HttpStatus.OK);
    }
}
