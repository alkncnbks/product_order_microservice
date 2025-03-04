package com.example.order_api.service;

import com.example.order_api.dto.OrderDTO;
import com.example.order_api.model.Order;
import com.example.order_api.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    //@Transactional
    //public Order createOrder(Order order) {
    //    if (order.getProduct() <= 0) {
    //        throw new IllegalArgumentException("Quantity must be greater than zero");
    //    }
    //    return orderRepository.save(order);
    //}

    public String orderProcessing(){
        //api should be 3rd party payment gateway
        return new Random().nextBoolean()?"success":"false";
    }

    public List<OrderDTO> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    public OrderDTO createOrder(OrderDTO orderDTO){
        Order order = convertToEntity(orderDTO);

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(order);
    }

    public OrderDTO updateOrder(long id, OrderDTO orderDTO){
        Order order = convertToEntity(orderDTO);
        order.setId(id);
        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    public void deleteOrder(long id){
        orderRepository.deleteById(id);
    }

    private OrderDTO convertToDTO(Order order){
        return OrderDTO.builder()
                .id(order.getId())
                .product(order.getProduct())
                .address(order.getAddress())
                .phone(order.getPhone())
                .orderStatus(orderProcessing())
                .build();
    }

    private Order convertToEntity(OrderDTO orderDTO){
        return Order.builder()
                .id(orderDTO.getId())
                .product(orderDTO.getProduct())
                .address(orderDTO.getAddress())
                .phone(orderDTO.getPhone())
                .orderStatus(orderProcessing())
                .build();
    }
}
