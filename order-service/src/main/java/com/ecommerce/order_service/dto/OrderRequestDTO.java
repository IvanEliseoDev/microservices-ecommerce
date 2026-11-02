package com.ecommerce.order_service.dto;

import com.ecommerce.order_service.model.OrderLineItems;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class OrderRequestDTO {

    @NotEmpty(message = "La orden debe contener almenos un item")
    @Valid
    private List<OrderLineItems> orderLineItemsList;
}
