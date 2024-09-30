package com.libraryapi.services.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import com.libraryapi.repository.OrderRepo;
import com.libraryapi.entities.Orders;
import com.libraryapi.entities.Book;
import com.libraryapi.entities.User;
import com.libraryapi.exceptions.ResourceNotFoundException;
import com.libraryapi.payloads.OrderDto;
import com.libraryapi.payloads.UserDto;
import com.libraryapi.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ModelMapper modelmapper;

    @Autowired
    private OrderRepo orderRepo;

    
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @Override
    public OrderDto createOrder(Integer userId, Integer bookId) {
        Orders order = new Orders();

        order.setBook(this.bookServiceImpl.fetchBook(bookId));
        order.setUser(this.userServiceImpl.fetchUser(userId));
        order.setDate(new Date());
        Orders saved = this.orderRepo.save(order);
        return this.modelmapper.map(saved, OrderDto.class);
    }


    @Override
    public OrderDto getOrderById(Integer orderId) {
         Orders order = this.orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));
        return this.modelmapper.map(order, OrderDto.class);
     }
     @Override
    public OrderDto getOrderByUserAndBook(Integer userId,Integer bookId) {
        User user = this.userServiceImpl.fetchUser(userId);
        Book book = this.bookServiceImpl.fetchBook(bookId);
         Orders order = this.orderRepo.findByUserAndBook(user,book);
         OrderDto orderDto = this.modelmapper.map(order, OrderDto.class);       
         orderDto.setUser(this.modelmapper.map(user, UserDto.class));
        return orderDto;
     }

    @Override
    public void deleteOrder(Integer orderId) {
        Orders order = this.orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));
        this.orderRepo.delete(order);
        }

    @Override
    public List<OrderDto> getAllOrdersByUser(Integer userId) {
         User user = this.userServiceImpl.fetchUser(userId);
        List<Orders>orders = this.orderRepo.findByUser(user);
        // List<LikesDto> likesDtos =  likes.stream().map((like) -> {
        //     LikesDto likeDto = this.modelMapper.map(like, LikesDto.class);
        //     UserDto user = this.modelMapper.map(like.getUser(), UserDto.class);
        //     // LikesDto.setUser(user);

        //     return likeDto;
        // }).collect(Collectors.toList());

        List<OrderDto> orderDtos =  orders.stream().map((order) -> this.modelmapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;    
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Orders>orders = this.orderRepo.findAll();
        List<OrderDto> orderDtos =  orders.stream().map((order) -> this.modelmapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

}
