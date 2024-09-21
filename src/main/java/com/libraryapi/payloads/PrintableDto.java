package com.libraryapi.payloads;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PrintableDto {
    private Integer printId;

    private String address;

    private String city;

    private String stateContry;

    private Integer pincode;
    
    private Long phoneNumber;
    
    private Date printOrderdate;

    private boolean isdelivered;

    private String passState;

    private Date expectedDate;

    private UserDto user;

    private BookDto book;
}
