package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.Product;
import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.exception.ResourceNotFoundException;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository  productRepository;

    private final ProductMapper mapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        Product product = mapper.toProduct(requestDTO);

        Product savedProduct = productRepository.save(product);
        log.info("Producto: {}, creado exitosamente", savedProduct.getName());

        return mapper.toProductResponseDTO(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> getAllsProducts() {
        return productRepository.findAll().stream().map(mapper::toProductResponseDTO).toList();
    }

    @Override
    public ProductResponseDTO getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
        log.info("Producto con el id: {}, encontrado exitosamente", id);
        return mapper.toProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProductById(String id, ProductRequestDTO updateRequestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
        mapper.updateProductFromRequest(updateRequestDTO, product);

        Product updateProduct = productRepository.save(product);
        log.info("Producto: {}, actualizado exitosamente", updateProduct.getName());
        return mapper.toProductResponseDTO(updateProduct);
    }

    @Override
    public void deleteProductById(String id) {

        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Producto", "id", id);
        }

        productRepository.deleteById(id);
        log.info("Producto con el id: {}, eliminado exitosamente", id);
    }
}
