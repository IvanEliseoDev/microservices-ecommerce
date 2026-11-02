package com.ecommerce.inventory_service.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryRequestDTO {

    @NotBlank(message = "El SKU no debe de ir vacio")
    private String sku;

    @Min(value =  0 , message = "La cantidad no debe de ser menor a 0")
    private Integer quantity;
}
