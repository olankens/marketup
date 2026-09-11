package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO productRequest);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(String id);

    ProductResponseDTO updateProductById(String id, ProductRequestDTO productRequest);

    void deleteProduct(String id);
}