package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    List<ProductResponseDTO> getAllsProducts();
    ProductResponseDTO getProductById(String id);
    ProductResponseDTO updateProductById(String id, ProductRequestDTO updateRequestDTO);
    void deleteProductById(String id);
}
