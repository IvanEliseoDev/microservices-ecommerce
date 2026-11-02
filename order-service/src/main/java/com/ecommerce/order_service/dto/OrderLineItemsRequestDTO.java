package com.ecommerce.order_service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor @NoArgsConstructor
public class OrderLineItemsRequestDTO {
    @NotBlank(message = "El SKU es obligatorio, no debe de ir vacio")
    private String sku;

    @NotNull(message = "El precio es obligatorio, no debe de ir nulo")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull(message = "La cantidad es obligatoria, no debe de ir nulo")
    @Min(value = 1)
    @Positive(message = "La cantidad debe de ser un numero positivo y arriba de 0")
    private Integer quantity;
}
