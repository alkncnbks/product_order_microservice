package com.example.productapi.client;

import com.example.productapi.config.FeignClientConfig;
import com.example.productapi.dto.OrderDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name= "order-service", url="http://localhost:8092/api/orders",configuration = FeignClientConfig.class)
public interface OrderClient {

    @PostMapping
    OrderDTO createProduct(@RequestBody OrderDTO orderDTO);
}
