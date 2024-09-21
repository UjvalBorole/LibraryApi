
package com.libraryapi.payloads;

import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Integer orderId;

    private Date date;

    private UserDto user;

    private BookDto book;
}
