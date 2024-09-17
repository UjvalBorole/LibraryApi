package com.libraryapi.payloads;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String Message;
    private Boolean success;
}
