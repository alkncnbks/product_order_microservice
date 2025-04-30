package com.example.productapi.dto;
import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

@Builder
@Data
public class ProductDTO {
    @NotNull(message = "Product ID cannot be null")
    private Long id;

    @NotBlank(message = "Product name cannot be empty")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    private String name;

    @NotNull(message = "Product price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price should not exceed 10 digits in total with 2 decimal places")
    private Double price;

    @NotBlank(message = "Product description cannot be empty")
    @Size(max = 500, message = "Product description cannot exceed 500 characters")
    private String description;
}
