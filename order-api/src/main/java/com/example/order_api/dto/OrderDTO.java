package com.example.order_api.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderDTO {
    private Long id;
    private String product;
    private String address;
    private String phone;
    private String orderStatus;
}
