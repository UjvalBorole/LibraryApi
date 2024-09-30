package com.libraryapi.services;
import java.util.*;

import com.libraryapi.payloads.OrderDto;

public interface OrderService {
    OrderDto createOrder(Integer userId,Integer bookId);
    void deleteOrder(Integer orderId);
     OrderDto getOrderByUserAndBook(Integer userId,Integer bookId);
    
    OrderDto getOrderById(Integer orderId);
    List<OrderDto>getAllOrdersByUser(Integer userId);
    List<OrderDto>getAllOrders();
    
}
