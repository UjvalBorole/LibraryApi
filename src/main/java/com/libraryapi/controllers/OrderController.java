package com.libraryapi.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libraryapi.payloads.ApiResponse;
import com.libraryapi.payloads.OrderDto;
import com.libraryapi.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/user/{userId}/book/{bookId}")
    public ResponseEntity<OrderDto>createOrder(
         @PathVariable Integer userId, @PathVariable Integer bookId
    ){
        OrderDto orderDto = this.orderService.createOrder(userId, bookId);
        return new ResponseEntity<>(orderDto,HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse deleteOrder(@PathVariable Integer orderId){
        this.orderService.deleteOrder(orderId);
        return new ApiResponse("Order was Deleted Successfully",true);
    }
    //getAllLikesByBook
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>>getAllOrdersByUser(
        @PathVariable Integer userId
    ){
        List<OrderDto>orderDtos = this.orderService.getAllOrdersByUser(userId);
        return new ResponseEntity<>(orderDtos,HttpStatus.OK);
    }

    //getAllLikesByBook
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto>getOrderById(
        @PathVariable Integer orderId
    ){
        OrderDto orderDtos = this.orderService.getOrderById(orderId);
        return new ResponseEntity<>(orderDtos,HttpStatus.OK);
    }

     //getAllLikesByBook
     @GetMapping("/")
     public ResponseEntity<List<OrderDto>>getAllOrders(){
         List<OrderDto>orderDtos = this.orderService.getAllOrders();
         return new ResponseEntity<>(orderDtos,HttpStatus.OK);
     }

}

