package com.example.productapi.service;

import com.example.productapi.client.OrderClient;
import com.example.productapi.dto.OrderDTO;
import com.example.productapi.dto.ProductDTO;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final OrderClient orderClient;
    private final ProductRepository productRepository;
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    public ProductService(OrderClient orderClient, ProductRepository productRepository) {
        this.orderClient = orderClient;
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) throws Exception {
        LOG.info("ProductDTO {} for: {}", HttpStatus.OK, productDTO);
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        OrderDTO orderDTO = OrderDTO.builder().product(productDTO.getName()).build();
        OrderDTO responseOrderDTO = orderClient.createProduct(orderDTO);
        LOG.info("OrderStatus {} for: {}", HttpStatus.OK, responseOrderDTO.getOrderStatus());
        if(Objects.equals(responseOrderDTO.getOrderStatus(), "false")) {
            throw new RuntimeException();
        }
        return convertToDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    private Product convertToEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .build();
    }
}
