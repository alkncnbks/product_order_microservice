package com.example.productapi.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderDTO {
    private String product;
    private String address;
    private String phone;
    private String orderStatus;
}
