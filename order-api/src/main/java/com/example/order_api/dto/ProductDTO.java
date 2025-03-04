package com.example.order_api.dto;
import lombok.Data;
import lombok.Builder;

@Builder
@Data
public class ProductDTO {
    private String name;
    private Double price;
}