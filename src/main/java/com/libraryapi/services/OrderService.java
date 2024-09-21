package com.libraryapi.services;
import java.util.*;

import com.libraryapi.payloads.OrderDto;

public interface OrderService {
    OrderDto createOrder(Integer userId,Integer bookId);
    void deleteOrder(Integer orderId);
    
    OrderDto getOrderById(Integer orderId);
    List<OrderDto>getAllOrdersByUser(Integer userId);
    List<OrderDto>getAllOrders();
    
}
