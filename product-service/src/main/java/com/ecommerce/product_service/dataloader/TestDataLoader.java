package com.ecommerce.product_service.dataloader;

import com.ecommerce.product_service.dto.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TestDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {

        Product product = Product.builder()
                          .name("Iphone 11")
                          .description("Iphone con IA")
                          .price(BigDecimal.valueOf(1200))
                          .build();

        productRepository.save(product);
        System.out.println("Datos de prueba cargados exitosamente");
    }
}
